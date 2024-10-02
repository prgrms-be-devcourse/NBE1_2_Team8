package org.prgrms.devconnect.api.controller.chatting.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ChatPartResponse(
        Long chatpartId,
        Long roomId
) {
}
