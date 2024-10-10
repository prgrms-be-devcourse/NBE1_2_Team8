package org.prgrms.devconnect.api.service.chatting;

import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.chatting.dto.response.ChatRoomListResponse;
import org.prgrms.devconnect.api.controller.chatting.dto.response.MessageFullResponse;
import org.prgrms.devconnect.api.controller.chatting.dto.response.MessageResponse;
import org.prgrms.devconnect.common.exception.ExceptionCode;
import org.prgrms.devconnect.common.exception.chatting.ChattingException;
import org.prgrms.devconnect.domain.define.chatting.entity.ChattingRoom;
import org.prgrms.devconnect.domain.define.chatting.repository.ChatParticipationRepository;
import org.prgrms.devconnect.domain.define.chatting.repository.ChattingRoomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChattingQueryService {

  private final ChatParticipationRepository chatParticipationRepository;
  private final ChattingRoomRepository chattingRoomRepository;

  //활성화된 채팅방을 사용자 ID로 조회하는 메서드
  public List<ChatRoomListResponse> findAllActivateChattingsByMemberId(Long memberId){
    return chatParticipationRepository.findByMemberIdAndStatusIsActive(memberId);
  }

  // 채팅방 메세지 조회하는 메서드
  public MessageFullResponse getAllMessagebyRoomId(Long roomId, Pageable pageable){
    Page<MessageResponse> content = chatParticipationRepository.findAllMessageByRoomId(roomId, pageable);
    return new MessageFullResponse(roomId, content);
  }

  //채팅방 ID로 채팅방 엔티티를 가져오는 메서드
  public ChattingRoom getChatRoomById(Long chatroomId){
    return chattingRoomRepository.findById(chatroomId)
            .orElseThrow(() -> new ChattingException(ExceptionCode.NOT_FOUND_CHATROOM));
  }
}
