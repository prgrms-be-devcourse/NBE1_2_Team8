package org.prgrms.devconnect.member.service;

import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.member.dto.MemberLoginRequestDto;
import org.prgrms.devconnect.member.entity.Member;
import org.prgrms.devconnect.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQueryService {

  private final MemberRepository memberRepository;

  public void login(MemberLoginRequestDto requestDto) {

    Member findMember = memberRepository.findByEmail(requestDto.email()).orElseThrow(
        () -> new RuntimeException("존재하지 않는 회원입니다.")
    );

    if (!findMember.validatePassword(requestDto.password())) {
      throw new RuntimeException("비밀번호가 일치하지 않습니다.");
    }
  }
}
