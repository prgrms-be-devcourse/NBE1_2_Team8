package org.prgrms.devconnect.api.controller.chatting.dto.request;

import jakarta.validation.constraints.NotNull;

public record ChatRoomRequest(
        @NotNull
        Long receiver_id
) {
}
