package org.prgrms.devconnect.api.controller.chatting.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record MessageFullResponse(
        @Schema(description = "채팅 방 ID", example = "100")
        Long roomId,

        @Schema(description = "메시지 목록", implementation = MessageResponse.class)
        Page<MessageResponse> messageList
) {
}
