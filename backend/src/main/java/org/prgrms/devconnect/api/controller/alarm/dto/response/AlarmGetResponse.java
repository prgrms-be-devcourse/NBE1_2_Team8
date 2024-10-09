package org.prgrms.devconnect.api.controller.alarm.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Builder;
import org.prgrms.devconnect.domain.define.alarm.entity.Alarm;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "단일 알림 응답 정보")
public record AlarmGetResponse(

        @Schema(description = "알림 수신자 닉네임", example = "홍길동")
        String sender,

        @Schema(description = "알림 생성 시각", example = "2024-00-00T00:00:00")
        LocalDateTime createdTime,

        @Schema(description = "알림 메시지", example = "알림 메시지가 도착했어요!")
        String content,

        @Schema(description = "읽음표시: true - 읽음, false - 읽지않음", example = "true")
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
