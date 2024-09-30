package org.prgrms.devconnect.api.service.member;

import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.member.dto.request.MemberLoginRequestDto;
import org.prgrms.devconnect.common.exception.ExceptionCode;
import org.prgrms.devconnect.common.exception.member.MemberException;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.prgrms.devconnect.domain.define.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQueryService {

  private final MemberRepository memberRepository;

  public void loginMember(MemberLoginRequestDto requestDto) {
    Member member = getMemberByEmailOrThrow(requestDto.email());
    if (!member.isValidPassword(requestDto.password())) {
      throw new MemberException(ExceptionCode.INVALID_PASSWORD);
    }
  }

  public Member getMemberByIdOrThrow(Long memberId) {
    return memberRepository.findByMemberId(memberId).orElseThrow(
            () -> new MemberException(ExceptionCode.NOT_FOUND_MEMBER)
    );
  }

  public Member getMemberByEmailOrThrow(String email) {
    return memberRepository.findByEmail(email).orElseThrow(
        () -> new MemberException(ExceptionCode.NOT_FOUND_MEMBER)
    );
  }

  public void validateDuplicatedEmail(String email) {
    if (memberRepository.existsByEmail(email))
      throw new MemberException(ExceptionCode.DUPLICATED_MEMBER_EMAIL);
  }
}
