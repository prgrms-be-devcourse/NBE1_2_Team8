package org.prgrms.devconnect.api.service.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.prgrms.devconnect.domain.define.fixture.MemberFixture.createMember;
import static org.prgrms.devconnect.domain.define.fixture.MemberFixture.createMemberCreateRequestDto;
import static org.prgrms.devconnect.domain.define.fixture.MemberFixture.createMemberUpdateRequestDto;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.devconnect.api.controller.member.dto.request.MemberCreateRequestDto;
import org.prgrms.devconnect.api.controller.member.dto.request.MemberUpdateRequestDto;
import org.prgrms.devconnect.api.service.techstack.TechStackQueryService;
import org.prgrms.devconnect.common.exception.ExceptionCode;
import org.prgrms.devconnect.common.exception.member.MemberException;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.prgrms.devconnect.domain.define.member.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
class MemberCommandServiceTest {

  @Mock
  private MemberRepository memberRepository;

  @Mock
  private TechStackQueryService techStackQueryService;

  @Mock
  private MemberQueryService memberQueryService;

  @InjectMocks
  private MemberCommandService memberCommandService;

  @DisplayName("유효한_RequestDto가_주어지면_멤버를_생성한다")
  @Test
  void 유효한_RequestDto가_주어지면_멤버를_생성한다() {
    // given
    MemberCreateRequestDto requestDto = createMemberCreateRequestDto();
    Member member = createMember("email@email.com");

    doNothing().when(memberQueryService).validateDuplicatedEmail(requestDto.email());
    when(techStackQueryService.getTechStacksByIdsOrThrow(any()))
        .thenReturn(List.of());
    when(memberRepository.save(any(Member.class)))
        .thenReturn(member);

    // when
    Member createdMember = memberCommandService.createMember(requestDto);

    // then
    verify(memberRepository).save(any(Member.class));
    verify(memberQueryService).validateDuplicatedEmail(requestDto.email());
  }

  @DisplayName("멤버 생성 시 중복된 이메일이 주어지면 예외를 발생시킨다")
  @Test
  void 중복된_이메일이_주어지면_예외를_발생시킨다() {
    // given
    MemberCreateRequestDto requestDto = createMemberCreateRequestDto("duplicate@email.com");

    // 중복 이메일 검증을 할 때 예외를 발생시킨다.
    doThrow(new MemberException(ExceptionCode.DUPLICATED_MEMBER_EMAIL))
        .when(memberQueryService).validateDuplicatedEmail(requestDto.email());

    // when & then
    assertThatThrownBy(() -> memberCommandService.createMember(requestDto))
        .isInstanceOf(MemberException.class)
        .hasMessage(ExceptionCode.DUPLICATED_MEMBER_EMAIL.getMessage());

    // memberRepository.save()는 호출되지 않아야 함
    verify(memberRepository, times(0)).save(any(Member.class));
  }

  @DisplayName("멤버 수정시 유효한 멤버 ID와 DTO가 주어지면 멤버를 업데이트한다")
  @Test
  void 유효한_멤버_ID와_DTO가_주어지면_업데이트한다() {
    // given
    Long memberId = 1L;
    Member existingMember = createMember("existing@email.com", "password", "nickname");
    MemberUpdateRequestDto updateRequestDto = createMemberUpdateRequestDto("updateNickname");

    when(memberQueryService.getMemberByIdWithTechStackOrThrow(memberId)).thenReturn(existingMember);

    // when
    memberCommandService.updateMember(memberId, updateRequestDto);

    // then
    verify(memberQueryService, times(1)).getMemberByIdWithTechStackOrThrow(memberId);
    assertThat(existingMember.getNickname()).isEqualTo("updateNickname");
  }

  @DisplayName("멤버 수정시 존재하지 않는 멤버 ID가 주어지면 예외를 발생시킨다")
  @Test
  void 존재하지_않는_멤버_ID가_주어지면_예외를_발생시킨다() {
    // given
    Long memberId = 100L;
    MemberUpdateRequestDto updateRequestDto = createMemberUpdateRequestDto("updateNickname");

    when(memberQueryService.getMemberByIdWithTechStackOrThrow(memberId))
        .thenThrow(new MemberException(ExceptionCode.NOT_FOUND_MEMBER));

    // when & then
    assertThatThrownBy(() -> memberCommandService.updateMember(memberId, updateRequestDto))
        .isInstanceOf(MemberException.class)
        .hasMessage(ExceptionCode.NOT_FOUND_MEMBER.getMessage());

    verify(memberQueryService, times(1)).getMemberByIdWithTechStackOrThrow(memberId);
  }
}
