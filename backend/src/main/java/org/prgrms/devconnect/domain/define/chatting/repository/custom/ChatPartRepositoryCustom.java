package org.prgrms.devconnect.domain.define.chatting.repository.custom;

import org.prgrms.devconnect.api.controller.chatting.dto.response.ChatRoomListResponse;

import java.util.List;

public interface ChatPartRepositoryCustom {

  List<ChatRoomListResponse> findByMemberIdAndStatusIsActive(Long memberId);
}
