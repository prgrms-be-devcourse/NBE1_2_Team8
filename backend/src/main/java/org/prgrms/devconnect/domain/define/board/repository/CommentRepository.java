package org.prgrms.devconnect.domain.define.board.repository;

import org.prgrms.devconnect.domain.define.board.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long>{
  @Query("SELECT c FROM Comment c WHERE c.board.boardId = :boardId")
  Page<Comment> findAllByBoardId(@Param("boardId") Long boardId, Pageable pageable);
}
