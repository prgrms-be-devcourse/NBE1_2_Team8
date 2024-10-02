package org.prgrms.devconnect.api.service.comment;

import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.common.exception.ExceptionCode;
import org.prgrms.devconnect.common.exception.comment.CommentException;
import org.prgrms.devconnect.domain.define.board.entity.Comment;
import org.prgrms.devconnect.domain.define.board.repository.CommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentQueryService {
  private final CommentRepository commentRepository;

  public Comment getCommentByIdOrThrow(Long commentId) {
    return commentRepository.findById(commentId)
            .orElseThrow(() -> new CommentException(ExceptionCode.NOT_FOUND_COMMENT));
  }

  public Page<Comment> findAllByBoardId(Long boardId, Pageable pageable){
    return commentRepository.findAllByBoardId(boardId,pageable);
  }
}
