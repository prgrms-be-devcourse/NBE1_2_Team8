package org.prgrms.devconnect.api.service.alarm;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.alarm.dto.response.AlarmsGetResponse;
import org.prgrms.devconnect.api.service.member.MemberQueryService;
import org.prgrms.devconnect.common.exception.ExceptionCode;
import org.prgrms.devconnect.common.exception.alarm.AlarmException;
import org.prgrms.devconnect.domain.define.alarm.entity.Alarm;
import org.prgrms.devconnect.domain.define.alarm.repository.AlarmRepository;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlarmQueryService {

  private final AlarmRepository alarmRepository;

  private final MemberQueryService memberQueryService;

  public AlarmsGetResponse getAlarmsByMemberId(Long memberId) {

    Member member = memberQueryService.getMemberByIdOrThrow(memberId);
    List<Alarm> alarms = alarmRepository.findAllByMember(member);

    alarms.forEach(Alarm::updateAlarmStatusToRead);

    return AlarmsGetResponse.from(alarms);
  }

  public Alarm getAlarmByAlarmIdAndMemberIdOrThrow (Long alarmId, Long memberId) {
    return alarmRepository.findByAlarmIdAndMemberMemberId(alarmId,memberId).orElseThrow(
            () -> new AlarmException(ExceptionCode.NOT_FOUND_ALARM)
    );
  }
}
