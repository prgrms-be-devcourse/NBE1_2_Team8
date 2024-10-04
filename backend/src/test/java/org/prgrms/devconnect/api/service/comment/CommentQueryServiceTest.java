package org.prgrms.devconnect.api.service.comment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.devconnect.api.controller.comment.dto.response.CommentResponseDto;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentQueryServiceTest {
  @InjectMocks
  private CommentQueryService commentQueryService;

  @Mock
  private CommentRepository commentRepository;

  @Mock
  private Comment comment;

  @Mock
  private Member member;

  @Mock
  private Board board;

  @Test
  void 댓글ID로_댓글_조회_성공(){
    //given
    Long commentId = 1L;
    when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

    //when
    Comment result=commentQueryService.getCommentByIdOrThrow(commentId);

    //then
    assertNotNull(result);
    verify(commentRepository).findById(commentId);
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
    verify(commentRepository).findById(commentId);
  }

  @Test
  void 게시물_ID로_댓글_페이징_조회_성공(){
    //given
    Long boardId=1L;
    Pageable pageable=Pageable.ofSize(10);
    List<Comment> commentList = comments();
    Page<Comment> comments = new PageImpl<>(commentList, pageable, 1);

    when(commentRepository.findAllByBoardId(boardId,pageable)).thenReturn(comments);
    //when
    Page<CommentResponseDto>result=commentQueryService.getCommentsByBoardId(boardId,pageable);

    //then
    verify(commentRepository).findAllByBoardId(boardId,pageable);
    assertThat(result.getTotalElements()).isEqualTo(commentList.size());
  }

  private List<Comment> comments(){
    List<Comment> comments = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      comments.add(CommentFixture.createComment(member, board, "댓글 테스트"));
    }
    return comments;
  }

  }
