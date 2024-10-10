package org.prgrms.devconnect.domain.define.chatting.repository;


import org.prgrms.devconnect.domain.define.chatting.entity.ChatParticipation;
import org.prgrms.devconnect.domain.define.chatting.repository.custom.ChatPartRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatParticipationRepository extends JpaRepository<ChatParticipation, Long>, ChatPartRepositoryCustom {
  List<ChatParticipation> findAllByChattingRoom_RoomId(Long roomId);

  Optional<ChatParticipation> findByMember_MemberIdAndChattingRoom_RoomId(Long memberId, Long chatRoomId);

}
