package org.prgrms.devconnect.api.service.comment;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.comment.dto.request.CommentCreateRequestDto;
import org.prgrms.devconnect.api.controller.comment.dto.response.CommentResponseDto;
import org.prgrms.devconnect.api.service.board.BoardQueryService;
import org.prgrms.devconnect.api.service.member.MemberQueryService;
import org.prgrms.devconnect.common.exception.ExceptionCode;
import org.prgrms.devconnect.common.exception.comment.CommentException;
import org.prgrms.devconnect.domain.define.board.entity.Board;
import org.prgrms.devconnect.domain.define.board.entity.Comment;
import org.prgrms.devconnect.domain.define.board.repository.CommentRepository;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentCommandService {
  private final CommentRepository commentRepository;
  private final MemberQueryService memberQueryService;
  private final BoardQueryService boardQueryService;
  private final CommentQueryService commentQueryService;

  public void createComment(CommentCreateRequestDto commentCreateRequestDto) {
    Member member = memberQueryService.getMemberByIdOrThrow(commentCreateRequestDto.memberId());
    Board board = boardQueryService.getBoardByIdOrThrow(commentCreateRequestDto.boardId());

    Comment parentComment = null;
    if (commentCreateRequestDto.parentId() != null) {
      parentComment = commentQueryService.getCommentByIdOrThrow(commentCreateRequestDto.parentId());

      //최상위 댓글은 parent필드가 null이어야함.
      if(parentComment.getParent()!=null){
        throw new CommentException(ExceptionCode.INVALID_PARENT_COMMENT);
      }
    }

    Comment comment = commentCreateRequestDto.toEntity(member, board, parentComment);
    commentRepository.save(comment);
  }

  public Page<CommentResponseDto> getCommentsByBoardId(Long boardId, Pageable pageable) {
    boardQueryService.getBoardByIdOrThrow(boardId);
    Page<Comment> comments  =commentQueryService.findAllByBoardId(boardId,pageable);
    return comments.map(comment-> {
              Member member=comment.getMember();
              Comment parent= comment.getParent();
              return CommentResponseDto.builder()
                      .commentId(comment.getCommentId())
                      .memberId(member.getMemberId())
                      .author(member.getNickname())
                      .content(comment.getContent())
                      .updatedAt(comment.getUpdatedAt())
                      .parentId(parent != null ? parent.getCommentId() : null)
                      .build();
            });
  }
}
