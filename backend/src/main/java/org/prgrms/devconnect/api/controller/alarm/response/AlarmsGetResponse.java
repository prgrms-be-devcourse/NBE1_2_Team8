package org.prgrms.devconnect.api.controller.alarm.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import org.prgrms.devconnect.domain.define.alarm.entity.Alarm;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AlarmsGetResponse(
        int count,
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
