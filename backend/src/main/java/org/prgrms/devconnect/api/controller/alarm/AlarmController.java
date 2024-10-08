package org.prgrms.devconnect.api.controller.alarm;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.alarm.dto.response.AlarmsGetResponse;
import org.prgrms.devconnect.api.service.alarm.AlarmCommandService;
import org.prgrms.devconnect.api.service.alarm.AlarmQueryService;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/alarms")
public class AlarmController {

  private final AlarmQueryService alarmQueryService;
  private final AlarmCommandService alarmCommandService;

  @GetMapping
  public ResponseEntity<AlarmsGetResponse> getAlarms(@AuthenticationPrincipal Member member) {
    return ResponseEntity.status(OK)
        .body(alarmQueryService.getAlarmsByMemberIdOrThrow(member.getMemberId()));
  }

  @DeleteMapping()
  public ResponseEntity<Void> deleteAlarmsByMemberId(@AuthenticationPrincipal Member member) {
    return ResponseEntity.status(NO_CONTENT)
        .body(alarmCommandService.deleteAlarmsByMemberId(member.getMemberId()));
  }

  @DeleteMapping("/{alarmId}")
  public ResponseEntity<Void> deleteAlarm(@AuthenticationPrincipal Member member,
      @PathVariable Long alarmId) {
    return ResponseEntity.status(NO_CONTENT)
        .body(alarmCommandService.deleteAlarmByAlarmIdAndMemberId(alarmId, member.getMemberId()));
  }

  @GetMapping("/counts")
  public ResponseEntity<Integer> getUnReadAlarmsCount(@AuthenticationPrincipal Member member) {
    return ResponseEntity.status(OK)
        .body(alarmQueryService.getUnReadAlarmsCountByMemberId(member.getMemberId()));
  }
}
