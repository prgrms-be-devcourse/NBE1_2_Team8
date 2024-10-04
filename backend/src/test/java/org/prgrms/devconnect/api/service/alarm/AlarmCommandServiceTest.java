package org.prgrms.devconnect.api.service.alarm;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
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
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class AlarmCommandServiceTest {


  @MockBean
  private MemberCommandService memberCommandService;  // MockBean으로 변경

  @MockBean
  private AlarmRepository alarmRepository;

  @MockBean
  private AlarmQueryService alarmQueryService;

  @Autowired
  private AlarmCommandService alarmCommandService;


  @Test
  @DisplayName("회원가입 시 웰컴 메시지로직이 실행되는지 검증")
  void verifyWelcomeMessageIsSavedOnSignUp() {

    MemberCreateRequestDto memberCreateRequestDto = mock(MemberCreateRequestDto.class);
    Member member = mock(Member.class);

    when(memberCommandService.createMember(memberCreateRequestDto)).thenReturn(member);
    alarmCommandService.createWelcomeAlarmWhenSignIn(member);

    verify(alarmRepository, times(1)).save(any(Alarm.class));
  }

  @Test
  @DisplayName("알림 단일 삭제")
  void deleteAlarm() {
    Alarm alarm = mock();
    Member member = mock();

    Optional<Alarm> alarmOptional = Optional.of(alarm);

    when(alarm.getAlarmId()).thenReturn(1L);
    when(alarm.getMember()).thenReturn(member);
    when(member.getMemberId()).thenReturn(1L);
    when(alarmQueryService.getAlarmByAlarmIdAndMemberIdOrThrow(1L,1L)).thenReturn(alarm);

    alarmCommandService.deleteAlarmByAlarmIdAndMemberId(alarm.getAlarmId(), alarm.getMember().getMemberId());

    verify(alarmRepository, times(1)).deleteByAlarmIdAndMemberMemberId(anyLong(), anyLong());
  }

}
