package org.prgrms.devconnect.chatting.service;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.chatting.entity.ChattingRoom;
import org.prgrms.devconnect.chatting.repository.ChatParticipationRepository;
import org.prgrms.devconnect.chatting.repository.ChattingRoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChattingService {

  private final ChattingRoomRepository chattingRoomRepository;
  private final ChatParticipationRepository chatParticipationRepository;

  // 채팅방 비활성화 서비스
  public void closeChattingRoom(Long chatroomId){
    ChattingRoom chattingRoom = chattingRoomRepository.findById(chatroomId).orElseThrow(() -> new RuntimeException("ChattingRoom not found id : " + chatroomId));
    chattingRoom.closeChatRoom();
    chattingRoomRepository.save(chattingRoom);
  }
}
