package org.prgrms.devconnect.api.service.member;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.member.dto.request.MemberCreateRequestDto;
import org.prgrms.devconnect.api.controller.member.dto.request.MemberTechStackRequestDto;
import org.prgrms.devconnect.common.exception.ExceptionCode;
import org.prgrms.devconnect.common.exception.techstack.TechStackException;
import org.prgrms.devconnect.domain.define.alarm.aop.RegisterPublisher;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.prgrms.devconnect.domain.define.member.entity.MemberTechStackMapping;
import org.prgrms.devconnect.domain.define.member.repository.MemberRepository;
import org.prgrms.devconnect.domain.define.techstack.entity.TechStack;
import org.prgrms.devconnect.domain.define.techstack.repository.TechStackRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberCommandService {

  private final MemberRepository memberRepository;
  private final TechStackRepository techStackRepository;
  private final MemberQueryService memberQueryService;

  @RegisterPublisher
  public Member createMember(MemberCreateRequestDto requestDto) {
    memberQueryService.validateDuplicatedEmail(requestDto.email());

    List<Long> techStackIds = extractTechStackIds(requestDto);
    Map<Long, TechStack> techStackMap = fetchTechStacks(techStackIds);

    List<MemberTechStackMapping> memberTechStacks = convertToMemberTechStacks(
        requestDto.techStackRequests(), techStackMap);

    Member member = requestDto.toEntity(memberTechStacks);
    memberRepository.save(member);

    return member;
  }



  private List<Long> extractTechStackIds(MemberCreateRequestDto requestDto) {
    return requestDto.techStackRequests()
        .stream()
        .map(MemberTechStackRequestDto::techStackId)
        .collect(Collectors.toList());
  }

  private Map<Long, TechStack> fetchTechStacks(List<Long> techStackIds) {
    return techStackRepository.findAllByTechStackIdIn(techStackIds)
        .stream()
        .collect(Collectors.toMap(TechStack::getTechStackId, Function.identity()));
  }

  private List<MemberTechStackMapping> convertToMemberTechStacks(
      List<MemberTechStackRequestDto> memberTechStackRequestDtos,
      Map<Long, TechStack> techStackMap) {

    return memberTechStackRequestDtos.stream()
        .map(dto -> mapToMemberStack(dto, techStackMap))
        .collect(Collectors.toList());
  }

  private MemberTechStackMapping mapToMemberStack(MemberTechStackRequestDto dto,
      Map<Long, TechStack> techStackMap) {

    TechStack techStack = techStackMap.get(dto.techStackId());

    if (techStack == null) {
      throw new TechStackException(ExceptionCode.NOT_FOUND_TECH_STACK);
    }

    return dto.toEntity(techStack);
  }
}
