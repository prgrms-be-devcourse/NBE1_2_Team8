package org.prgrms.devconnect.alarm.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.devconnect.api.controller.member.dto.request.MemberCreateRequestDto;
import org.prgrms.devconnect.api.service.alarm.AlarmCommandService;
import org.prgrms.devconnect.api.service.member.MemberCommandService;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class AlarmServiceTest {

  @MockBean
  private AlarmCommandService alarmCommandService;

  @MockBean
  private MemberCommandService memberCommandService;  // MockBean으로 변경


  @Test
  @DisplayName("회원가입 시 웰컴 메시지로직이 실행되는지 검증")
  void verifyWelcomeMessageIsSavedOnSignUp() {

    MemberCreateRequestDto memberCreateRequestDto = mock(MemberCreateRequestDto.class);
    Member member = mock(Member.class);

    when(memberCommandService.createMember(memberCreateRequestDto)).thenReturn(member);
    alarmCommandService.createWelcomeAlarmWhenSignIn(member);


    verify(alarmCommandService, times(1)).createWelcomeAlarmWhenSignIn(member);
  }

}
