package org.prgrms.devconnect.api.service.comment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.devconnect.common.exception.ExceptionCode;
import org.prgrms.devconnect.common.exception.comment.CommentException;
import org.prgrms.devconnect.domain.define.board.entity.Comment;
import org.prgrms.devconnect.domain.define.board.repository.CommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentQueryServiceTest {
  @InjectMocks
  private CommentQueryService commentQueryService;

  @Mock
  private CommentRepository commentRepository;

  @Test
  void 댓글ID로_댓글_조회_성공(){
    //given
    Long commentId = 1L;
    Comment comment =mock(Comment.class);
    when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

    //when
    Comment result=commentQueryService.getCommentByIdOrThrow(commentId);

    //then
    assertNotNull(result);
    verify(commentRepository,times(1)).findById(commentId);
  }

  @Test
  void 댓글ID로_댓글_조회_실패(){
    //given
    Long commentId = 1L;
    when(commentRepository.findById(commentId)).thenReturn(Optional.empty());

    //when
    CommentException exception=assertThrows(CommentException.class,()-> {
      commentQueryService.getCommentByIdOrThrow(commentId);
    });

    assertEquals(ExceptionCode.NOT_FOUND_COMMENT, exception.getExceptionCode());
    verify(commentRepository,times(1)).findById(commentId);
  }

  @Test
  void 게시판ID로_댓글_페이징_조회_성공(){
    //given
    Long boardId=1L;
    Pageable pageable = Pageable.ofSize(10);
    Comment comment =mock(Comment.class);
    Page<Comment> comments = new PageImpl<>(Collections.singletonList(comment), pageable, 1);
    when(commentQueryService.findAllByBoardId(boardId, pageable)).thenReturn(comments);

    //when
    Page<Comment>result=commentQueryService.findAllByBoardId(boardId, pageable);

    //then
    assertNotNull(result);
    assertEquals(1,result.getTotalElements());
    verify(commentRepository,times(1)).findAllByBoardId(boardId, pageable);
  }


  }
