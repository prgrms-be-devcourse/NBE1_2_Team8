package org.prgrms.devconnect.domain.define.chatting.repository;

import org.prgrms.devconnect.domain.define.chatting.entity.ChattingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChattingRoomRepository extends JpaRepository<ChattingRoom, Long> {
}
