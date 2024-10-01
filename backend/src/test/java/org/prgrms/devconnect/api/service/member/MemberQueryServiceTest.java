package org.prgrms.devconnect.api.service.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.prgrms.devconnect.domain.define.fixture.MemberFixture.createMember;
import static org.prgrms.devconnect.domain.define.fixture.MemberFixture.createMemberLoginRequestDto;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.devconnect.api.controller.member.dto.request.MemberLoginRequestDto;
import org.prgrms.devconnect.common.exception.ExceptionCode;
import org.prgrms.devconnect.common.exception.member.MemberException;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.prgrms.devconnect.domain.define.member.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
class MemberQueryServiceTest {

  @Mock
  private MemberRepository memberRepository;

  @InjectMocks
  private MemberQueryService memberQueryService;

  @DisplayName("로그인 시 유효한 이메일과 비밀번호가 주어지면 멤버가 로그인된다")
  @Test
  void 로그인_성공() {
    // given
    String email = "test@email.com";
    String password = "password123";
    Member member = createMember(email, password);
    MemberLoginRequestDto requestDto = createMemberLoginRequestDto(email, password);

    when(memberRepository.findByEmail(email)).thenReturn(Optional.of(member));

    // when
    memberQueryService.loginMember(requestDto);

    // then
    verify(memberRepository, times(1)).findByEmail(email);
  }

  @DisplayName("로그인 시 비밀번호가 잘못되면 예외를 발생시킨다")
  @Test
  void 로그인_비밀번호_잘못된_경우() {
    // given
    String email = "test@email.com";
    String password = "wrongPassword";
    Member member = createMember(email);

    MemberLoginRequestDto requestDto = createMemberLoginRequestDto(email, password);

    when(memberRepository.findByEmail(email)).thenReturn(Optional.of(member));

    // when & then
    assertThatThrownBy(() -> memberQueryService.loginMember(requestDto))
        .isInstanceOf(MemberException.class)
        .hasMessage(ExceptionCode.INVALID_PASSWORD.getMessage());
  }

  @DisplayName("멤버 ID로 멤버를 조회할 때 존재하지 않으면 예외를 발생시킨다")
  @Test
  void 멤버_ID로_조회_예외_발생() {
    // given
    Long memberId = 100L;
    when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

    // when & then
    assertThatThrownBy(() -> memberQueryService.getMemberByIdOrThrow(memberId))
        .isInstanceOf(MemberException.class)
        .hasMessage(ExceptionCode.NOT_FOUND_MEMBER.getMessage());
  }

  @DisplayName("멤버 ID로 멤버를 조회할 때 존재하면 멤버 정보를 반환한다")
  @Test
  void 멤버_ID로_조회_성공() {
    // given
    Long memberId = 1L;
    Member member = createMember("test");
    when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));

    // when
    Member foundMember = memberQueryService.getMemberByIdOrThrow(memberId);

    // then
    assertThat(foundMember).isEqualTo(member);
  }

  @DisplayName("이메일로 멤버를 조회할 때 존재하지 않으면 예외를 발생시킨다")
  @Test
  void 이메일로_조회_예외_발생() {
    // given
    String email = "notfound@email.com";
    when(memberRepository.findByEmail(email)).thenReturn(Optional.empty());

    // when & then
    assertThatThrownBy(() -> memberQueryService.getMemberByEmailOrThrow(email))
        .isInstanceOf(MemberException.class)
        .hasMessage(ExceptionCode.NOT_FOUND_MEMBER.getMessage());
  }

  @DisplayName("이메일로 멤버를 조회할 때 존재하면 멤버 정보를 반환한다")
  @Test
  void 이메일로_조회_성공() {
    // given
    String email = "exist@email.com";
    Member member = createMember(email);
    when(memberRepository.findByEmail(email)).thenReturn(Optional.of(member));

    // when
    Member foundMember = memberQueryService.getMemberByEmailOrThrow(email);

    // then
    assertThat(foundMember).isEqualTo(member);
  }

  @DisplayName("중복 이메일 검증 시 중복된 이메일이 있으면 예외를 발생시킨다")
  @Test
  void 중복이메일검증_예외발생() {
    // given
    String email = "duplicate@email.com";
    when(memberRepository.existsByEmail(email)).thenReturn(true);

    // when & then
    assertThatThrownBy(() -> memberQueryService.validateDuplicatedEmail(email))
        .isInstanceOf(MemberException.class)
        .hasMessage(ExceptionCode.DUPLICATED_MEMBER_EMAIL.getMessage());
  }

  @DisplayName("중복 이메일 검증 시 중복되지 않은 이메일이 주어지면 아무런 예외도 발생하지 않는다")
  @Test
  void 중복이메일검증_성공() {
    // given
    String email = "unique@email.com";
    when(memberRepository.existsByEmail(email)).thenReturn(false);

    // when
    memberQueryService.validateDuplicatedEmail(email);

    // then
    verify(memberRepository, times(1)).existsByEmail(email);
  }
}