package org.prgrms.devconnect.api.controller.alarm.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import org.prgrms.devconnect.domain.define.alarm.entity.Alarm;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "전체 알림 응답 정보")
public record AlarmsGetResponse(
        @Schema(description = "전체 알림의 개수", example = "123")
        int count,
        @Schema(description = "여러 개의 단일 알림 응답 정보")
        List<AlarmGetResponse> alarms
) {
  public static AlarmsGetResponse from(List<Alarm> alarms) {
    return AlarmsGetResponse.builder()
            .count(alarms.size())
            .alarms(alarms.stream()
                    .map(AlarmGetResponse::from)
                    .collect(Collectors.toList()))
            .build();
  }
}
