package org.prgrms.devconnect.api.controller.chatting.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.data.domain.Page;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record MessageFullResponse(
        Long roomId,
        Page<MessageResponse> messageList
) {
}
