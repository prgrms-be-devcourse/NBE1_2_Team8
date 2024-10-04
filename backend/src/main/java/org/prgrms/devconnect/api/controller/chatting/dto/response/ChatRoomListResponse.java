package org.prgrms.devconnect.api.controller.chatting.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import org.prgrms.devconnect.domain.define.chatting.entity.constant.ChattingRoomStatus;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ChatRoomListResponse(
        @Schema(description = "멤버 ID", example = "2")
        Long memberId,

        @Schema(description = "채팅 참여자 ID", example = "1")
        Long chatpartId,

        @Schema(description = "채팅 방 ID", example = "100")
        Long roomId,

        @Schema(description = "채팅 방 상태", example = "ACTIVE")
        ChattingRoomStatus status
) {
}
