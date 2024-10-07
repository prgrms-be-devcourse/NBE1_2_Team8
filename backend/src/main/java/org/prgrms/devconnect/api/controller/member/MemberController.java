package org.prgrms.devconnect.api.controller.member;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.member.dto.request.MemberCreateRequestDto;
import org.prgrms.devconnect.api.controller.member.dto.request.MemberLoginRequestDto;
import org.prgrms.devconnect.api.controller.member.dto.request.MemberUpdateRequestDto;
import org.prgrms.devconnect.api.controller.member.dto.response.MemberResponseDto;
import org.prgrms.devconnect.api.service.member.MemberCommandService;
import org.prgrms.devconnect.api.service.member.MemberQueryService;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

  private final MemberCommandService memberCommandService;
  private final MemberQueryService memberQueryService;

  @GetMapping
  public ResponseEntity<MemberResponseDto> getMember(@AuthenticationPrincipal Member member) {
    MemberResponseDto responseDto = memberQueryService.getMember(member.getMemberId());
    return ResponseEntity.ok(responseDto);
  }

  @PostMapping("/signup")
  public ResponseEntity<Void> createMember(@RequestBody @Valid MemberCreateRequestDto dto) {
    memberCommandService.createMember(dto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PostMapping("/login")
  public void login(@RequestBody @Valid MemberLoginRequestDto dto) {
  }

  @PostMapping("/logout")
  public void logout() {
  }

  @PutMapping("/{memberId}")
  public ResponseEntity<Void> updateMember(@PathVariable Long memberId,
      @RequestBody @Valid MemberUpdateRequestDto dto) {
    memberCommandService.updateMember(memberId, dto);
    return ResponseEntity.ok().build();
  }

}
