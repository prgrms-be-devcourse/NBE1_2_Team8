package org.prgrms.devconnect.api.controller.chatting.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.prgrms.devconnect.domain.define.chatting.entity.constant.ChattingRoomStatus;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ChatRoomListResponse(
        Long memberId, //TODO 닉네임 추가 필요
        // memberId, nickname {Long chatpartId, Long roomId, ChattingRoomStatus status ... 같은 응답형태로 변경필요}
        Long chatpartId,
        Long roomId,
        ChattingRoomStatus status
) {
}
