package org.prgrms.devconnect.api.service.comment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.devconnect.api.controller.comment.dto.request.CommentCreateRequestDto;
import org.prgrms.devconnect.api.controller.comment.dto.request.CommentUpdateRequestDto;
import org.prgrms.devconnect.api.service.board.BoardQueryService;
import org.prgrms.devconnect.api.service.member.MemberQueryService;
import org.prgrms.devconnect.common.exception.ExceptionCode;
import org.prgrms.devconnect.common.exception.comment.CommentException;
import org.prgrms.devconnect.domain.define.board.entity.Board;
import org.prgrms.devconnect.domain.define.board.entity.Comment;
import org.prgrms.devconnect.domain.define.board.repository.CommentRepository;
import org.prgrms.devconnect.domain.define.fixture.CommentFixture;
import org.prgrms.devconnect.domain.define.member.entity.Member;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentCommandServiceTest {

  @InjectMocks
  private CommentCommandService commentCommandService;

  @Mock
  private CommentRepository commentRepository;

  @Mock
  private MemberQueryService memberQueryService;

  @Mock
  private BoardQueryService boardQueryService;

  @Mock
  private CommentQueryService commentQueryService;


  @Test
  public void 댓글_생성_성공(){
    // given
    CommentCreateRequestDto commentCreateRequestDto=CommentFixture.createCommentCreateRequestDto();
    Member member=mock(Member.class);
    Board board=mock(Board.class);
    Comment comment=mock(Comment.class);
    when(memberQueryService.getMemberByIdOrThrow(any())).thenReturn(member);
    when(boardQueryService.getBoardByIdOrThrow(any())).thenReturn(board);
    doReturn(comment).when(commentRepository).save(any(Comment.class));

    //when
    commentCommandService.createComment(commentCreateRequestDto);

    //then
    verify(memberQueryService).getMemberByIdOrThrow(any(Long.class));
    verify(boardQueryService).getBoardByIdOrThrow(any(Long.class));
    verify(commentRepository).save(any(Comment.class));
  }

  @Test
  void 댓글_생성_실패_부모댓글이_최상위_댓글이_아님(){
    //given
    CommentCreateRequestDto commentCreateRequestDto=CommentFixture.createCommentCreateRequestDtoWithParentId();
    Member member=mock(Member.class);
    Board board=mock(Board.class);
    Comment parentComment=mock(Comment.class);

    when(memberQueryService.getMemberByIdOrThrow(any())).thenReturn(member);
    when(boardQueryService.getBoardByIdOrThrow(any())).thenReturn(board);
    when(commentQueryService.getCommentByIdOrThrow(any())).thenReturn(parentComment);

    //when
    CommentException commentException=assertThrows(CommentException.class,()->{
            commentCommandService.createComment(commentCreateRequestDto);
    });

    //then
    assertEquals(ExceptionCode.INVALID_PARENT_COMMENT,commentException.getExceptionCode());
    verify(commentQueryService).getCommentByIdOrThrow(any());
    verify(commentRepository,never()).save(any(Comment.class));
  }

  @Test
  void 댓글_수정_성공() {
    // given
    Long commentId = 1L;
    CommentUpdateRequestDto commentUpdateRequestDto = CommentFixture.createCommentUpdateRequestDto();
    Comment comment = mock(Comment.class);

    when(commentQueryService.getCommentByIdOrThrow(commentId)).thenReturn(comment);

    // when
    commentCommandService.updateComment(commentId, commentUpdateRequestDto);

    // then
    verify(commentQueryService).getCommentByIdOrThrow(commentId);
    verify(comment).updateFromDto(commentUpdateRequestDto);
  }

  @Test
  void 존재하지_않는_댓글_ID가_주어지면_예외를_발생시킨다(){
    //given
    Long commentId = 100L;
    CommentUpdateRequestDto commentUpdateRequestDto = CommentFixture.createCommentUpdateRequestDto();

    when(commentQueryService.getCommentByIdOrThrow(commentId))
            .thenThrow(new CommentException(ExceptionCode.NOT_FOUND_COMMENT));

    //when
    CommentException commentException=assertThrows(CommentException.class,()->{
      commentCommandService.updateComment(commentId,commentUpdateRequestDto);
    });
    //then
    assertEquals(ExceptionCode.NOT_FOUND_COMMENT,commentException.getExceptionCode());
    verify(commentQueryService).getCommentByIdOrThrow(commentId);
    verify(commentRepository,never()).save(any(Comment.class));
  }

  @Test
  void 댓글_삭제_성공() {
    // given
    Long commentId = 1L;
    Comment comment = mock(Comment.class);

    when(commentQueryService.getCommentByIdOrThrow(commentId)).thenReturn(comment);

    // when
    commentCommandService.deleteComment(commentId);

    // then
    verify(commentQueryService).getCommentByIdOrThrow(commentId);
    verify(commentRepository).delete(comment);
  }
}

