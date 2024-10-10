package org.prgrms.devconnect.domain.define.chatting.repository.custom;

import org.prgrms.devconnect.api.controller.chatting.dto.response.ChatRoomListResponse;
import org.prgrms.devconnect.api.controller.chatting.dto.response.MessageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChatPartRepositoryCustom {

  List<ChatRoomListResponse> findByMemberIdAndStatusIsActive(Long memberId);

  Page<ChatRoomListResponse> findByMemberIdAndStatusIsActiveWithPage(Long memberId, Pageable pageable);

  Page<MessageResponse> findAllMessageByRoomId(Long roomId, Pageable pageable);
}
