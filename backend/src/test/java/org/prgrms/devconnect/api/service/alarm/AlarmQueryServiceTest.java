package org.prgrms.devconnect.api.service.alarm;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.devconnect.api.controller.alarm.dto.response.AlarmsGetResponse;
import org.prgrms.devconnect.api.service.member.MemberQueryService;
import org.prgrms.devconnect.common.exception.alarm.AlarmException;
import org.prgrms.devconnect.domain.define.alarm.entity.Alarm;
import org.prgrms.devconnect.domain.define.alarm.repository.AlarmRepository;
import org.prgrms.devconnect.domain.define.member.entity.Member;

@ExtendWith(MockitoExtension.class)
class AlarmQueryServiceTest {

  @InjectMocks
  private AlarmQueryService alarmQueryService;

  @Mock
  private AlarmRepository alarmRepository;

  @Mock
  private MemberQueryService memberQueryService;

  @Mock
  private Member member;

  private List<Alarm> alarms(){
    List<Alarm> alarms = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      alarms.add(new Alarm(member, "알림테스트", "테스트유알엘", false));
    }
    return alarms;
  }

  @Test
  @DisplayName("알림 전체 조회")
  void getAlarmsByMemberId() {
    List<Alarm> alarms = alarms();

    //given
    doReturn(1L).when(member)
            .getMemberId();
    doReturn(alarms).when(alarmRepository)
            .findAllByMember(any(Member.class));
    doReturn(member).when(memberQueryService).getMemberByIdOrThrow(any(Long.class));

    //when
    final AlarmsGetResponse response = alarmQueryService.getAlarmsByMemberIdOrThrow(member.getMemberId());

    //then
    assertThat(alarms.get(0).isRead()).isEqualTo(true);
    assertThat(response.count()).isEqualTo(5);

    verify(member).getMemberId();
    verify(alarmRepository).findAllByMember(member);
    verify(memberQueryService).getMemberByIdOrThrow(member.getMemberId());
  }

  @Test
  @DisplayName("한 알림에 대하여 알림아이디와 멤베 아이디가 일치하지 않을 경우 예외 처리")
  void getAlarmByAlarmIdAndMemberIdOrThrow() {
    doReturn(Optional.empty()).when(alarmRepository).findByAlarmIdAndMemberMemberId(anyLong(), anyLong());

    Assertions.assertThatThrownBy(() -> alarmQueryService.getAlarmByAlarmIdAndMemberIdOrThrow(anyLong(), anyLong()))
            .isInstanceOf(AlarmException.class);
  }
}