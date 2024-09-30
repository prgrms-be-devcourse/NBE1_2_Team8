package org.prgrms.devconnect.api.service.member;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.member.dto.request.MemberCreateRequestDto;
import org.prgrms.devconnect.api.controller.member.dto.request.MemberUpdateRequestDto;
import org.prgrms.devconnect.api.service.techstack.TechStackQueryService;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.prgrms.devconnect.domain.define.member.entity.MemberTechStackMapping;
import org.prgrms.devconnect.domain.define.member.repository.MemberRepository;
import org.prgrms.devconnect.domain.define.techstack.entity.TechStack;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberCommandService {

  private final MemberRepository memberRepository;
  private final TechStackQueryService techStackQueryService;
  private final MemberQueryService memberQueryService;

  public void createMember(MemberCreateRequestDto requestDto) {
    memberQueryService.validateDuplicatedEmail(requestDto.email());

    List<MemberTechStackMapping> memberTechStacks = getTechStackMappings(requestDto.techStackIds());

    Member member = requestDto.toEntity(memberTechStacks);
    memberRepository.save(member);
  }

  // TODO MemberTechStack도 업데이트 하는 로직 추후 구현
  public void updateMember(Long memberId, MemberUpdateRequestDto requestDto) {
    Member member = memberQueryService.getMemberByIdOrThrow(memberId);
    member.updateFromDto(requestDto);
  }

  private List<MemberTechStackMapping> getTechStackMappings(List<Long> techStackIds) {
    List<TechStack> techStacks = techStackQueryService.getTechStacksByIdsOrThrow(techStackIds);
    return techStacks.stream()
        .map(techStack -> MemberTechStackMapping.builder().techStack(techStack).build())
        .collect(Collectors.toList());
  }

}
