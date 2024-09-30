package org.prgrms.devconnect.api.service.chatting;

import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.chatting.dto.response.ChatRoomListResponse;
import org.prgrms.devconnect.domain.define.chatting.repository.ChatParticipationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChattingQueryService {

  private final ChatParticipationRepository chatParticipationRepository;

  public List<ChatRoomListResponse> findAllActivateChattingsByMemberId(Long memberId){
    List<ChatRoomListResponse> results = chatParticipationRepository.findByMemberIdAndStatusIsActive(memberId);
    return results;
  }
}
