package org.prgrms.devconnect.api.controller.chatting.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.prgrms.devconnect.domain.define.chatting.entity.constant.ChattingRoomStatus;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ChatRoomListResponse(
        Long memberId,
        Long roomId,
        ChattingRoomStatus status
) {
}
