package org.prgrms.devconnect.api.service.member;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.prgrms.devconnect.api.controller.member.dto.request.MemberCreateRequestDto;
import org.prgrms.devconnect.api.controller.member.dto.request.MemberUpdateRequestDto;
import org.prgrms.devconnect.api.service.techstack.TechStackQueryService;
import org.prgrms.devconnect.common.auth.redis.RefreshToken;
import org.prgrms.devconnect.common.auth.redis.RefreshTokenRepository;
import org.prgrms.devconnect.common.exception.ExceptionCode;
import org.prgrms.devconnect.common.exception.refresh.RefreshTokenException;
import org.prgrms.devconnect.domain.define.alarm.aop.RegisterPublisher;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.prgrms.devconnect.domain.define.member.entity.MemberTechStackMapping;
import org.prgrms.devconnect.domain.define.member.repository.MemberRepository;
import org.prgrms.devconnect.domain.define.member.repository.MemberTechStackMappingRepository;
import org.prgrms.devconnect.domain.define.techstack.entity.TechStack;
import org.prgrms.devconnect.domain.define.techstack.repository.TechStackRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberCommandService {

  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;
  private final TechStackQueryService techStackQueryService;
  private final MemberQueryService memberQueryService;
  private final RefreshTokenRepository refreshTokenRepository;
  private final MemberTechStackMappingRepository memberTechStackMappingRepository;
  private final TechStackRepository techStackRepository;
  private final EntityManager em;

  @RegisterPublisher
  public Member createMember(MemberCreateRequestDto requestDto) {
    memberQueryService.validateDuplicatedEmail(requestDto.email());

    List<MemberTechStackMapping> memberTechStacks = getTechStackMappings(requestDto.techStackIds());

    Member member = requestDto.toEntity(memberTechStacks);
    member.passwordEncode(passwordEncoder);
    memberRepository.save(member);

    return member;
  }

  public void updateMember(Long memberId, MemberUpdateRequestDto requestDto) {
    Member member = memberQueryService.getMemberByIdWithTechStackOrThrow(memberId);
    member.updateFromDto(requestDto);

    // 삭제할 TechStack 처리
    List<Long> deleteTechIds = requestDto.deleteTechStacks();
    if (deleteTechIds != null) {
      deleteTechStacksFromMember(member, deleteTechIds);
    }

    // 추가할 TechStack 처리
    List<Long> addTechIds = requestDto.addTechStacks();
    if (addTechIds != null) {
      addTechStacksToMember(member, addTechIds);
    }

    // 1차 캐시 초기화
//    em.flush();
//    em.clear();
  }

  private List<MemberTechStackMapping> getTechStackMappings(List<Long> techStackIds) {
    List<TechStack> techStacks = techStackQueryService.getTechStacksByIdsOrThrow(techStackIds);
    return techStacks.stream()
        .map(techStack -> MemberTechStackMapping.builder().techStack(techStack).build())
        .collect(Collectors.toList());
  }


  // 기술 스택 삭제 기능
  public void deleteTechStacksFromMember(Member member, List<Long> deleteTechIds) {
    List<Long> idsToDelete = memberTechStackMappingRepository
            .findAllByMember_MemberIdAndTechStack_TechStackIdIn(member.getMemberId(), deleteTechIds)
            .stream()
            .map(MemberTechStackMapping::getId)
            .collect(Collectors.toList());

    memberTechStackMappingRepository.deleteAllByIds(idsToDelete);
  }

  // 기술 스택 추가 기능
  public void addTechStacksToMember(Member member, List<Long> addTechIds) {
    List<TechStack> techStacks = techStackRepository.findAllByTechStackIdIn(addTechIds);


    if(!techStacks.isEmpty()){
      List<MemberTechStackMapping> mappingToSave = techStacks.stream()
              .map(techStack -> MemberTechStackMapping.builder()
                      .techStack(techStack)
                      .build())
              .collect(Collectors.toList());

      mappingToSave.forEach(mapping -> mapping.assignMember(member));

      memberTechStackMappingRepository.saveAll(mappingToSave);
    }
  }
  public void logout(String email) {
    log.info("[REDIS] Refresh Token 삭제 시도: {}", email);
    RefreshToken token = refreshTokenRepository.findByAuthKey(email).orElseThrow(
        () -> new RefreshTokenException(ExceptionCode.NOT_FOUND_REFRESH_TOKEN)
    );
    refreshTokenRepository.delete(token);
    log.info("[REDIS] Refresh Token 삭제 성공: {}", email);
  }
}
