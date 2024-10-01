package org.prgrms.devconnect.api.controller.alarm.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.time.LocalDateTime;
import lombok.Builder;
import org.prgrms.devconnect.domain.define.alarm.entity.Alarm;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AlarmGetResponse(
        String sender,
        LocalDateTime createdTime,
        String content,
        boolean isRead
) {
  public static AlarmGetResponse from(Alarm alarm) {
      return AlarmGetResponse.builder()
              .sender(alarm.getMember().getNickname())
              .createdTime(alarm.getCreatedAt())
              .content(alarm.getAlertText())
              .isRead(alarm.isRead())
              .build();
  }
}
