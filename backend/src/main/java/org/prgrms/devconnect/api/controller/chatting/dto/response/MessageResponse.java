package org.prgrms.devconnect.api.controller.chatting.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record MessageResponse(
        @Schema(description = "메시지 ID", example = "10")
        Long messageId,

        @Schema(description = "보낸 사람의 ID", example = "2")
        Long senderId,

        @Schema(description = "보낸 사람의 닉네임", example = "김철수")
        String nickname,

        @Schema(description = "메시지 내용", example = "안녕하세요!")
        String content,

        @Schema(description = "메시지 생성 시간", type = "string", format = "date-time", example = "2024-10-01T12:34:56")
        LocalDateTime createdAt
) {
}
