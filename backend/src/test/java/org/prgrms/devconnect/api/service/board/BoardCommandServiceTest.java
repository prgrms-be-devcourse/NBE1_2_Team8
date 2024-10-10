package org.prgrms.devconnect.api.service.board;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.devconnect.api.controller.board.dto.request.BoardCreateRequestDto;
import org.prgrms.devconnect.api.controller.board.dto.request.BoardTechStackRequestDto;
import org.prgrms.devconnect.api.controller.board.dto.request.BoardUpdateRequestDto;
import org.prgrms.devconnect.api.service.jobpost.JobPostQueryService;
import org.prgrms.devconnect.api.service.member.MemberQueryService;
import org.prgrms.devconnect.api.service.techstack.TechStackQueryService;
import org.prgrms.devconnect.common.exception.ExceptionCode;
import org.prgrms.devconnect.common.exception.board.BoardException;
import org.prgrms.devconnect.domain.define.board.entity.Board;
import org.prgrms.devconnect.domain.define.board.entity.constant.BoardStatus;
import org.prgrms.devconnect.domain.define.board.repository.BoardRepository;
import org.prgrms.devconnect.domain.define.jobpost.entity.JobPost;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.prgrms.devconnect.domain.define.techstack.entity.TechStack;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BoardCommandServiceTest {
  @InjectMocks
  private BoardCommandService boardCommandService;

  @Mock
  private BoardRepository boardRepository;

  @Mock
  private MemberQueryService memberQueryService;

  @Mock
  private TechStackQueryService techStackQueryService;

  @Mock
  private  JobPostQueryService jobPostQueryService;

  @Mock
  private BoardQueryService boardQueryService;

  @Mock
  private BoardCreateRequestDto boardCreateRequestDto;

  @Mock
  private BoardUpdateRequestDto boardUpdateRequestDto;

  @Mock
  private Member member;

  @Mock
  private JobPost jobPost;

  @Mock
  private Board board;

  @Mock
  private TechStack techStack;

  @Test
  void 게시물_생성_성공() {
    // given
    when(memberQueryService.getMemberByIdOrThrow(anyLong())).thenReturn(member);
    when(boardCreateRequestDto.jobPostId()).thenReturn(null); // JobPost는 없는 상태
    when(boardCreateRequestDto.toEntity(any(Member.class), isNull(), anyList())).thenReturn(board);
    when(board.getBoardId()).thenReturn(1L);
    when(boardCreateRequestDto.techStackRequests()).thenReturn(createBoardTechStackRequestDtos());
    when(techStackQueryService.getTechStacksByIdsOrThrow(anyList())).thenReturn(createTechStacks());

    // when
    Long boardId = boardCommandService.createBoard(boardCreateRequestDto);

    // then
    assertNotNull(boardId);
    verify(boardRepository).save(any(Board.class));
  }

  @Test
  void 공고와_연관된_게시물_생성_성공() {
    // given
    when(memberQueryService.getMemberByIdOrThrow(anyLong())).thenReturn(member);

    Long jobPostId=1L;
    when(boardCreateRequestDto.jobPostId()).thenReturn(jobPostId);
    when(jobPostQueryService.getJobPostByIdOrThrow(jobPostId)).thenReturn(jobPost);

    when(boardCreateRequestDto.techStackRequests()).thenReturn(createBoardTechStackRequestDtos());
    when(techStackQueryService.getTechStacksByIdsOrThrow(anyList())).thenReturn(createTechStacks());

    when(boardCreateRequestDto.toEntity(any(Member.class), any(JobPost.class), anyList())).thenReturn(board);
    when(board.getBoardId()).thenReturn(1L);

    // when
    Long boardId = boardCommandService.createBoard(boardCreateRequestDto);

    // then
    assertNotNull(boardId);
    verify(boardRepository).save(any(Board.class));
  }

  @Test
  void 게시물_수정_성공() {
    // given
    Long boardId = 1L;
    when(boardQueryService.getBoardByIdOrThrow(anyLong())).thenReturn(board);
    when(board.isDeleted()).thenReturn(false);
    when(board.getBoardId()).thenReturn(boardId);

    // when
    Long updatedBoardId = boardCommandService.updateBoard(boardId, boardUpdateRequestDto);

    // then
    assertEquals(1L,updatedBoardId);
    verify(board).updateFromDto(boardUpdateRequestDto);
  }

  @Test
  void 삭제된_게시물_수정_실패() {
    // given
    Long boardId = 1L;
    when(boardQueryService.getBoardByIdOrThrow(anyLong())).thenReturn(board);
    when(board.isDeleted()).thenReturn(true);

    // when
    BoardException exception = assertThrows(BoardException.class, () -> {
      boardCommandService.updateBoard(boardId, boardUpdateRequestDto);
    });

    // then
    assertEquals(ExceptionCode.NOT_FOUND_BOARD, exception.getExceptionCode());
  }


  @Test
  void 게시물_삭제_성공(){
    //given
    Long boardId = 1L;
    when(boardQueryService.getBoardByIdOrThrow(boardId)).thenReturn(board);

    //when
    boardCommandService.deleteBoard(boardId);

    //then
    verify(board).changeStatus(BoardStatus.DELETED);
  }

  @Test
  void 게시물_마감_성공(){
    //given
    Long boardId = 1L;
    when(boardQueryService.getBoardByIdOrThrow(boardId)).thenReturn(board);
    when(board.isClosed()).thenReturn(false);
    when(board.isDeleted()).thenReturn(false);

    //when
    boardCommandService.closeBoardManually(boardId);

    //then
    verify(board).changeStatus(BoardStatus.CLOSED);
  }

  @Test
  void 게시물_마감_실패(){
    //given
    Long boardId = 1L;
    when(boardQueryService.getBoardByIdOrThrow(boardId)).thenReturn(board);
    when(board.isClosed()).thenReturn(true);

    //when
    BoardException boardException=assertThrows(BoardException.class,()->{
      boardCommandService.closeBoardManually(boardId);
    });

    //then
    assertEquals(ExceptionCode.ALREADY_CLOSED_BOARD, boardException.getExceptionCode());
  }

  private List<BoardTechStackRequestDto> createBoardTechStackRequestDtos() {
    return List.of(new BoardTechStackRequestDto(1L));
  }

  private List<TechStack> createTechStacks() {
    return List.of(techStack);
  }
}
