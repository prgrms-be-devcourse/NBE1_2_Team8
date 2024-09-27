package org.prgrms.devconnect.member.controller;

import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.member.dto.MemberCreateRequestDto;
import org.prgrms.devconnect.member.dto.MemberLoginRequestDto;
import org.prgrms.devconnect.member.service.MemberCommandService;
import org.prgrms.devconnect.member.service.MemberQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

  private final MemberCommandService memberCommandService;
  private final MemberQueryService memberQueryService;

  @PostMapping("/signup")
  public ResponseEntity<?> createMember(@RequestBody MemberCreateRequestDto requestDto) {
    memberCommandService.createMember(requestDto);

    return ResponseEntity.status(201).body("회원 가입 완료.");
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody MemberLoginRequestDto requestDto) {
    memberQueryService.login(requestDto);

    return ResponseEntity.ok().body("로그인 성공");
  }
}
