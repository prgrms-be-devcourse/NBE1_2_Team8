package org.prgrms.devconnect.api.controller.alarm;

import static org.springframework.http.HttpStatus.OK;

import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.alarm.dto.response.AlarmsGetResponse;
import org.prgrms.devconnect.api.service.alarm.AlarmQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/alarms")
public class AlarmController {

  private final AlarmQueryService alarmQueryService;

  @GetMapping("/{memberId}")
  public ResponseEntity<AlarmsGetResponse> getAlarms(@PathVariable Long memberId) {
    return ResponseEntity.status(OK)
            .body(alarmQueryService.getAlarmsByMemberId(memberId));
  }
}
