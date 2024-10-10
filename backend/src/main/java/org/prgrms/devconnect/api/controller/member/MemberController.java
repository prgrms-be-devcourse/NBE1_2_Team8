package org.prgrms.devconnect.api.controller.member;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.member.dto.request.MemberCreateRequestDto;
import org.prgrms.devconnect.api.controller.member.dto.request.MemberLoginRequestDto;
import org.prgrms.devconnect.api.controller.member.dto.request.MemberUpdateRequestDto;
import org.prgrms.devconnect.api.controller.member.dto.response.MemberResponseDto;
import org.prgrms.devconnect.api.service.member.MemberCommandService;
import org.prgrms.devconnect.api.service.member.MemberQueryService;
import org.prgrms.devconnect.common.auth.JwtService;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
@Tag(name = "멤버 API", description = "회원 관리 관련 API")
public class MemberController {

  private final MemberCommandService memberCommandService;
  private final MemberQueryService memberQueryService;
  private final JwtService jwtService;

  @GetMapping
  @Operation(summary = "회원 정보 조회", description = "인증된 사용자의 회원 정보를 조회합니다.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "회원 정보 조회 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MemberResponseDto.class))),
          @ApiResponse(responseCode = "401", description = "인증 실패"),
          @ApiResponse(responseCode = "500", description = "서버 오류")
  })
  public ResponseEntity<MemberResponseDto> getMember(@AuthenticationPrincipal Member member) {
    MemberResponseDto responseDto = memberQueryService.getMember(member.getMemberId());
    return ResponseEntity.ok(responseDto);
  }

  @PostMapping("/signup")
  @Operation(summary = "회원 가입", description = "새로운 사용자를 등록합니다.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "회원 가입 성공"),
          @ApiResponse(responseCode = "400", description = "잘못된 요청"),
          @ApiResponse(responseCode = "500", description = "서버 오류")
  })
  public ResponseEntity<Void> createMember(@RequestBody @Valid MemberCreateRequestDto dto) {
    memberCommandService.createMember(dto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PostMapping("/login")
  @Operation(summary = "로그인", description = "사용자 로그인 처리.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "로그인 성공"),
          @ApiResponse(responseCode = "401", description = "인증 실패"),
          @ApiResponse(responseCode = "500", description = "서버 오류")
  })
  public void login(@RequestBody @Valid MemberLoginRequestDto dto) {
    // 로그인 처리 로직
  }

  @PostMapping("/logout")
  @Operation(summary = "로그아웃", description = "현재 로그인된 사용자를 로그아웃 처리.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "로그아웃 성공"),
          @ApiResponse(responseCode = "401", description = "인증 실패"),
          @ApiResponse(responseCode = "500", description = "서버 오류")
  })
  public ResponseEntity<Void> logout(@AuthenticationPrincipal Member member) {
    memberCommandService.logout(member.getEmail());
    return ResponseEntity.ok().build();
  }

  @PutMapping
  @Operation(summary = "회원 정보 수정", description = "현재 회원의 정보를 수정합니다.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "회원 정보 수정 성공"),
          @ApiResponse(responseCode = "400", description = "잘못된 요청"),
          @ApiResponse(responseCode = "500", description = "서버 오류")
  })
  public ResponseEntity<Void> updateMember(@AuthenticationPrincipal Member member,
                                           @RequestBody @Valid MemberUpdateRequestDto dto) {
    memberCommandService.updateMember(member.getMemberId(), dto);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/reissue")
  @Operation(summary = "Access Token 재발급", description = "Refresh Token을 사용하여 새로운 Access Token을 발급받습니다.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Access Token 재발급 성공"),
          @ApiResponse(responseCode = "401", description = "인증 실패"),
          @ApiResponse(responseCode = "500", description = "서버 오류")
  })
  public ResponseEntity<Void> reissueAccessToken(
          @CookieValue(value = "Authorization-refresh", defaultValue = "") String refreshToken,
          HttpServletResponse response) {
    jwtService.reIssueAccessToken(response, refreshToken);
    return ResponseEntity.ok().build();
  }

}
