package org.prgrms.devconnect.member.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.member.dto.MemberCreateRequestDto;
import org.prgrms.devconnect.member.entity.Member;
import org.prgrms.devconnect.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberCommandService {

  private final MemberRepository memberRepository;

  public void createMember(MemberCreateRequestDto requestDto) {
    Optional<Member> existMember = memberRepository.findByEmail(requestDto.email());

    if (existMember.isPresent()) {
      throw new RuntimeException("이미 존재하는 회원입니다.");
    }

    Member savedMember = requestDto.toEntity();

    memberRepository.save(savedMember);

  }
}
