package org.prgrms.devconnect.api.service.chatting;

import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.chatting.dto.response.ChatRoomListResponse;
import org.prgrms.devconnect.api.controller.chatting.dto.response.MessageFullResponse;
import org.prgrms.devconnect.api.controller.chatting.dto.response.MessageResponse;
import org.prgrms.devconnect.domain.define.chatting.repository.ChatParticipationRepository;
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

  //활성화된 채팅방을 사용자 ID로 조회하는 메서드
  public List<ChatRoomListResponse> findAllActivateChattingsByMemberId(Long memberId){
    List<ChatRoomListResponse> results = chatParticipationRepository.findByMemberIdAndStatusIsActive(memberId);
    return results;
  }

  public MessageFullResponse findAllMessagebyRoomId(Long roomId, Pageable pageable){
    Page<MessageResponse> content = chatParticipationRepository.findAllMessageByRoomId(roomId, pageable);
    return new MessageFullResponse(roomId, content);
  }
}
