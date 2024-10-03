package org.prgrms.devconnect.api.controller.alarm;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.alarm.dto.response.AlarmsGetResponse;
import org.prgrms.devconnect.api.service.alarm.AlarmCommandService;
import org.prgrms.devconnect.api.service.alarm.AlarmQueryService;
import org.springframework.http.ResponseEntity;
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

  @GetMapping("/{memberId}")
  public ResponseEntity<AlarmsGetResponse> getAlarms(@PathVariable Long memberId) {
    return ResponseEntity.status(OK)
            .body(alarmQueryService.getAlarmsByMemberId(memberId));
  }

  @DeleteMapping("{memberId}/{alarmId}")
  public ResponseEntity<Void> deleteAlarm(@PathVariable Long memberId, @PathVariable Long alarmId) {
    return ResponseEntity.status(NO_CONTENT)
            .body(alarmCommandService.deleteAlarmByAlarmIdAndMemberId(alarmId, memberId));
  }
}
