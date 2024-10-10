package org.prgrms.devconnect.domain.define.chatting.repository;

import org.prgrms.devconnect.domain.define.chatting.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
