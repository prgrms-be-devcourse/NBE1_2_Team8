package org.prgrms.devconnect.api.controller.chatting.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ChatRoomRequest(
        @NotNull
        @Schema(description = "수신자 ID", example = "2")
        Long receiverId
) {
}
