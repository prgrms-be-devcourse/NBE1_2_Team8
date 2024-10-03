package org.prgrms.devconnect.api.service.alarm;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.devconnect.api.controller.member.dto.request.MemberCreateRequestDto;
import org.prgrms.devconnect.api.service.member.MemberCommandService;
import org.prgrms.devconnect.common.exception.alarm.AlarmException;
import org.prgrms.devconnect.domain.define.alarm.entity.Alarm;
import org.prgrms.devconnect.domain.define.alarm.repository.AlarmRepository;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class AlarmCommandServiceTest {


  @MockBean
  private MemberCommandService memberCommandService;  // MockBean으로 변경

  @MockBean
  private AlarmRepository alarmRepository;

  @Autowired
  private AlarmCommandService alarmCommandService;


  @Test
  @DisplayName("회원가입 시 웰컴 메시지로직이 실행되는지 검증")
  void verifyWelcomeMessageIsSavedOnSignUp() {

    MemberCreateRequestDto memberCreateRequestDto = mock(MemberCreateRequestDto.class);
    Member member = mock(Member.class);

    when(memberCommandService.createMember(memberCreateRequestDto)).thenReturn(member);
    alarmCommandService.createWelcomeAlarmWhenSignIn(member);

    verify(alarmCommandService, times(1)).createWelcomeAlarmWhenSignIn(member);
  }

  @Test
  @DisplayName("알림 단일 삭제")
  void deleteAlarm() {

    doNothing().when(alarmRepository).deleteById(any(Long.class));

    alarmCommandService.deleteAlarmByAlarmIdAndMemberId(anyLong(), anyLong());

    verify(alarmRepository, times(1)).deleteById(any());
  }

  @Test
  @DisplayName("알림 단일 삭제시 존재하지 않은 알림(혹은 알림은 존재하지만 멤버가 다른 경우)의 예외 처리 테스트")
  void deleteAlarmWhenNotExistAlarmThrowException() {
    doThrow(new IllegalArgumentException()).when(alarmRepository).deleteById(any(Long.class));

    Assertions.assertThatThrownBy(() -> alarmCommandService.deleteAlarmByAlarmIdAndMemberId(any(Long.class)))
            .isInstanceOf(AlarmException.class);

    verify(alarmRepository, times(0)).delete(any(Alarm.class));
  }

}
