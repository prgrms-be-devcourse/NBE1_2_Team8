package org.prgrms.devconnect.api.service.comment;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.comment.dto.request.CommentCreateRequestDto;
import org.prgrms.devconnect.api.controller.comment.dto.request.CommentUpdateRequestDto;
import org.prgrms.devconnect.api.service.board.BoardQueryService;
import org.prgrms.devconnect.api.service.member.MemberQueryService;
import org.prgrms.devconnect.common.exception.ExceptionCode;
import org.prgrms.devconnect.common.exception.comment.CommentException;
import org.prgrms.devconnect.domain.define.alarm.aop.RegisterPublisher;
import org.prgrms.devconnect.domain.define.board.entity.Board;
import org.prgrms.devconnect.domain.define.board.entity.Comment;
import org.prgrms.devconnect.domain.define.board.repository.CommentRepository;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentCommandService {
  private final CommentRepository commentRepository;
  private final MemberQueryService memberQueryService;
  private final BoardQueryService boardQueryService;
  private final CommentQueryService commentQueryService;

  @RegisterPublisher
  public Comment createComment(CommentCreateRequestDto commentCreateRequestDto) {
    Member member = memberQueryService.getMemberByIdOrThrow(commentCreateRequestDto.memberId());
    Board board = boardQueryService.getBoardByIdOrThrow(commentCreateRequestDto.boardId());

    Comment parentComment = null;
    if (commentCreateRequestDto.parentId() != null) {
      parentComment = commentQueryService.getCommentByIdOrThrow(commentCreateRequestDto.parentId());
      if(!parentComment.isRootComment()){
        throw new CommentException(ExceptionCode.INVALID_PARENT_COMMENT);
      }
    }

    Comment comment = commentCreateRequestDto.toEntity(member, board, parentComment);
    commentRepository.save(comment);

    return comment;
  }

  public void updateComment(Long commentId,CommentUpdateRequestDto commentUpdateRequestDto){
    Comment comment = commentQueryService.getCommentByIdOrThrow(commentId);
    comment.updateFromDto(commentUpdateRequestDto);
    commentRepository.save(comment);
  }

  public void deleteComment(Long commentId){
    Comment comment = commentQueryService.getCommentByIdOrThrow(commentId);
    commentRepository.delete(comment);
  }
}
