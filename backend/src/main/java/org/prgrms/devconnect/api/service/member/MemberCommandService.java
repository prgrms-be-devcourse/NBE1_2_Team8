package org.prgrms.devconnect.api.service.member;

import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.member.dto.request.MemberCreateRequestDto;
import org.prgrms.devconnect.api.controller.member.dto.request.MemberUpdateRequestDto;
import org.prgrms.devconnect.api.service.techstack.TechStackQueryService;
import org.prgrms.devconnect.domain.define.alarm.aop.RegisterPublisher;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.prgrms.devconnect.domain.define.member.entity.MemberTechStackMapping;
import org.prgrms.devconnect.domain.define.member.repository.MemberRepository;
import org.prgrms.devconnect.domain.define.member.repository.MemberTechStackMappingRepository;
import org.prgrms.devconnect.domain.define.techstack.entity.TechStack;
import org.prgrms.devconnect.domain.define.techstack.repository.TechStackRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberCommandService {

  private final MemberRepository memberRepository;
  private final TechStackQueryService techStackQueryService;
  private final MemberQueryService memberQueryService;
  private final MemberTechStackMappingRepository memberTechStackMappingRepository;
  private final TechStackRepository techStackRepository;

  @RegisterPublisher
  public Member createMember(MemberCreateRequestDto requestDto) {
    memberQueryService.validateDuplicatedEmail(requestDto.email());

    List<MemberTechStackMapping> memberTechStacks = getTechStackMappings(requestDto.techStackIds());

    Member member = requestDto.toEntity(memberTechStacks);
    memberRepository.save(member);

    return member;
  }

  public void updateMember(Long memberId, MemberUpdateRequestDto requestDto) {
    Member member = memberQueryService.getMemberByIdWithTechStackOrThrow(memberId);
    member.updateFromDto(requestDto);

    // 삭제할 TechStack 처리
    List<Long> deleteTechIds = requestDto.deleteTechStacks();
    if (!deleteTechIds.isEmpty()) {
      deleteTechStacksFromMember(member, deleteTechIds);
    }

    // 추가할 TechStack 처리
    List<Long> addTechIds = requestDto.addTechStacks();
    if (!addTechIds.isEmpty()) {
      addTechStacksToMember(member, addTechIds);
    }
  }

  private List<MemberTechStackMapping> getTechStackMappings(List<Long> techStackIds) {
    List<TechStack> techStacks = techStackQueryService.getTechStacksByIdsOrThrow(techStackIds);
    return techStacks.stream()
            .map(techStack -> MemberTechStackMapping.builder().techStack(techStack).build())
            .collect(Collectors.toList());
  }


  // 기술 스택 삭제 기능
  private void deleteTechStacksFromMember(Member member, List<Long> deleteTechIds) {
    List<MemberTechStackMapping> mappingsToDelete = memberTechStackMappingRepository
            .findAllByMember_MemberIdAndTechStack_TechStackIdIn(member.getMemberId(), deleteTechIds);

    for (MemberTechStackMapping mapping : mappingsToDelete) {
      memberTechStackMappingRepository.delete(mapping);
    }
  }

  // 기술 스택 추가 기능
  private void addTechStacksToMember(Member member, List<Long> addTechIds) {
    List<TechStack> techStacks = techStackRepository.findAllByTechStackIdIn(addTechIds);

    for (TechStack techStack : techStacks) {
      MemberTechStackMapping newMapping = MemberTechStackMapping.builder()
              .techStack(techStack)
              .build();
      newMapping.assignMember(member);

      memberTechStackMappingRepository.save(newMapping);
    }
  }
}
