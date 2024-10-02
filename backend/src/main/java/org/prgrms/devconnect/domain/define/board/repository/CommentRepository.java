package org.prgrms.devconnect.domain.define.board.repository;

import org.prgrms.devconnect.domain.define.board.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
