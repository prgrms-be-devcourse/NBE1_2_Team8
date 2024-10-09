package org.prgrms.devconnect.api.controller.alarm;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "알림 API", description = "알림 관련 기능을 제공하는 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/alarms")
public class AlarmController {

  private final AlarmQueryService alarmQueryService;
  private final AlarmCommandService alarmCommandService;

  @Operation(summary = "알림 전체 조회", description = "사용자 아이디별 수신된 모든 알림을 반환합니다.")
  @ApiResponse(responseCode = "200", description = "수신된 모든 알림을 성공적으로 반환합니다.", content = @Content(schema = @Schema(implementation = AlarmsGetResponse.class)))
  @GetMapping
  public ResponseEntity<AlarmsGetResponse> getAlarms(@AuthenticationPrincipal Member member) {
    return ResponseEntity.status(OK)
        .body(alarmQueryService.getAlarmsByMemberIdOrThrow(member.getMemberId()));
  }
  
  @Operation(summary = "알림 전체 삭제", description = "사용자 아이디별 수신된 모든 알림을 삭제합니다.")
  @ApiResponse(responseCode = "204", description = "수신된 전체 알림 목록을 성공적으로 삭제했습니다.")
  @DeleteMapping
  public ResponseEntity<Void> deleteAlarmsByMemberId(@AuthenticationPrincipal Member member) {
    return ResponseEntity.status(NO_CONTENT)
        .body(alarmCommandService.deleteAlarmsByMemberId(member.getMemberId()));
  }

  @Operation(summary = "알림 단일 삭제", description = "특정 알림 id를 가진 알림을 삭제합니다.")
  @ApiResponse(responseCode = "204", description = "특정 알림 id를 가진 알림을 성공적으로 삭제했습니다.")
  @DeleteMapping("/{alarmId}")
  public ResponseEntity<Void> deleteAlarm(@AuthenticationPrincipal Member member,
      @PathVariable Long alarmId) {
    return ResponseEntity.status(NO_CONTENT)
        .body(alarmCommandService.deleteAlarmByAlarmIdAndMemberId(alarmId, member.getMemberId()));
  }


  @Operation(summary = "읽지 않은 알림 수 조회", description = "읽지 않은 알림의 수를 조회합니다.")
  @ApiResponse(responseCode = "200", description = "읽지 않은 알림의 수를 성공적으로 조회했습니다.", content = @Content(schema = @Schema(implementation = Integer.class)))
  @GetMapping("/counts")
  public ResponseEntity<Integer> getUnReadAlarmsCount(@AuthenticationPrincipal Member member) {
    return ResponseEntity.status(OK)
        .body(alarmQueryService.getUnReadAlarmsCountByMemberId(member.getMemberId()));
  }
    
}
