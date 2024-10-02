package org.prgrms.devconnect.api.service.comment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.devconnect.api.controller.comment.dto.request.CommentCreateRequestDto;
import org.prgrms.devconnect.api.controller.comment.dto.response.CommentResponseDto;
import org.prgrms.devconnect.api.service.board.BoardQueryService;
import org.prgrms.devconnect.api.service.member.MemberQueryService;
import org.prgrms.devconnect.common.exception.ExceptionCode;
import org.prgrms.devconnect.common.exception.comment.CommentException;
import org.prgrms.devconnect.domain.define.board.entity.Board;
import org.prgrms.devconnect.domain.define.board.entity.Comment;
import org.prgrms.devconnect.domain.define.board.repository.CommentRepository;
import org.prgrms.devconnect.domain.define.fixture.CommentFixture;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;

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
    verify(memberQueryService,times(1)).getMemberByIdOrThrow(any(Long.class));
    verify(boardQueryService,times(1)).getBoardByIdOrThrow(any(Long.class));
    verify(commentRepository, times(1)).save(any(Comment.class));
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
    when(parentComment.getParent()).thenReturn(mock(Comment.class));

    //when
    CommentException commentException=assertThrows(CommentException.class,()->{
            commentCommandService.createComment(commentCreateRequestDto);
    });

    //then
    assertEquals(ExceptionCode.INVALID_PARENT_COMMENT,commentException.getExceptionCode());
    verify(commentQueryService,times(1)).getCommentByIdOrThrow(any());
    verify(commentRepository,never()).save(any(Comment.class));
  }

  @Test
  void 게시물_ID로_댓글_페이징_조회_성공(){
    //given
    Long boardId=1L;
    Pageable pageable=Pageable.ofSize(10);
    Member member=mock(Member.class);
    Comment comment=mock(Comment.class);
    Comment parentComment=mock(Comment.class);

    when(comment.getMember()).thenReturn(member);
    when(comment.getParent()).thenReturn(parentComment);
    when(member.getMemberId()).thenReturn(1L);
    when(member.getNickname()).thenReturn("사용자");
    when(comment.getCommentId()).thenReturn(1L);
    when(comment.getContent()).thenReturn("테스트용 댓글");
    when(comment.getUpdatedAt()).thenReturn(LocalDateTime.now());

    Page<Comment> comments = new PageImpl<>(Collections.singletonList(comment), pageable, 1);

    when(boardQueryService.getBoardByIdOrThrow(boardId)).thenReturn(mock(Board.class));
    when(commentQueryService.findAllByBoardId(boardId,pageable)).thenReturn(comments);

    //when
    Page<CommentResponseDto>result=commentCommandService.getCommentsByBoardId(boardId,pageable);

    //then
    assertNotNull(result);
    assertEquals(1, result.getTotalElements());
    assertEquals(1L, result.getContent().get(0).commentId());
    assertEquals("사용자", result.getContent().get(0).author());
    assertEquals("테스트용 댓글", result.getContent().get(0).content());

    verify(boardQueryService,times(1)).getBoardByIdOrThrow(boardId);
    verify(commentQueryService,times(1)).findAllByBoardId(boardId,pageable);
  }
}
