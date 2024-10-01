package org.prgrms.devconnect.api.service.comment;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.comment.dto.CommentCreateRequestDto;
import org.prgrms.devconnect.api.service.board.BoardQueryService;
import org.prgrms.devconnect.api.service.member.MemberQueryService;
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

  public void createComment(CommentCreateRequestDto commentCreateRequestDto) {
    Member member = memberQueryService.getMemberByIdOrThrow(commentCreateRequestDto.memberId());
    Board board = boardQueryService.getBoardByIdOrThrow(commentCreateRequestDto.boardId());

    Comment parentComment = null;
    if (commentCreateRequestDto.parentId() != null) {
      parentComment = commentQueryService.getCommentByIdOrThrow(commentCreateRequestDto.parentId());
    }

    Comment comment = commentCreateRequestDto.toEntity(member, board, parentComment);

    if (parentComment != null) {
      parentComment.addChildComment(comment);
    }

    commentRepository.save(comment);
  }
}
