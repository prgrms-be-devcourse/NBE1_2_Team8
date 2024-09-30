package org.prgrms.devconnect.domain.define.chatting.entity.repository;


import org.prgrms.devconnect.domain.define.chatting.entity.ChatParticipation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatParticipationRepository extends JpaRepository<ChatParticipation, Long> {
  List<ChatParticipation> findAllByChattingRoom_RoomId(Long roomId);
}
