package org.prgrms.devconnect.api.service.board;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.devconnect.api.controller.board.dto.BoardFilterDto;
import org.prgrms.devconnect.api.controller.board.dto.response.BoardResponseDto;
import org.prgrms.devconnect.api.service.member.MemberQueryService;
import org.prgrms.devconnect.common.exception.board.BoardException;
import org.prgrms.devconnect.domain.define.board.entity.Board;
import org.prgrms.devconnect.domain.define.board.entity.constant.BoardCategory;
import org.prgrms.devconnect.domain.define.board.entity.constant.BoardStatus;
import org.prgrms.devconnect.domain.define.board.repository.BoardRepository;
import org.prgrms.devconnect.domain.define.fixture.BoardFixture;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.prgrms.devconnect.domain.define.techstack.entity.TechStack;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BoardQueryServiceTest {
  @InjectMocks
  private BoardQueryService boardQueryService;
  @Mock
  private BoardRepository boardRepository;
  @Mock
  private MemberQueryService memberQueryService;
  @Mock
  private Board board;

  @Test
  void 게시물ID로_게시물_조회_성공(){
    //given
    Long boardId = 1L;
    when(boardRepository.findByIdAndStatusNotDeleted(boardId)).thenReturn(Optional.of(board));

    //when
    Board result=boardQueryService.getBoardByIdOrThrow(boardId);

    //then
    verify(boardRepository).findByIdAndStatusNotDeleted(boardId);
    assertNotNull(result);
  }

  @Test
  void 게시물ID로_게시물_조회_실패(){
    //given
    Long boardId = 1L;
    when(boardRepository.findByIdAndStatusNotDeleted(boardId)).thenReturn(Optional.empty());

    //when
    BoardException exception=assertThrows(BoardException.class,()->{
      boardQueryService.getBoardByIdOrThrow(boardId);
    });

    //then
    verify(boardRepository).findByIdAndStatusNotDeleted(boardId);
    assertEquals("NOT_FOUND_BOARD", exception.getExceptionCode().name());
  }

  @Test
  void 전체_게시물_페이징_조회_성공(){
    //given
    Pageable pageable = Pageable.ofSize(10);
    List<Board> boardList = createBoards();
    Page<Board> boards=new PageImpl<>(boardList,pageable,boardList.size());
    when(boardRepository.findAllWithTechStackByStatusNotDeleted(pageable)).thenReturn(boards);

    //when
    Page<BoardResponseDto> result=boardQueryService.getAllBoards(pageable);

    //then
    verify(boardRepository).findAllWithTechStackByStatusNotDeleted(pageable);
    assertThat(result.getTotalElements()).isEqualTo(boardList.size());
  }

  @Test
  void 필터조건_없이_게시물_페이징_조회_성공() {
    // given
    Pageable pageable = Pageable.ofSize(10);
    List<Board> boardList = createBoards();
    Page<Board> boards=new PageImpl<>(boardList,pageable,boardList.size());
    when(boardRepository.findAllWithTechStackByStatusNotDeleted(pageable)).thenReturn(boards);

    // when
    Page<BoardResponseDto> result = boardQueryService.getBoardsByFilter(null, null, null, null, pageable);

    // then
    verify(boardRepository).findAllWithTechStackByStatusNotDeleted(pageable);
    assertThat(result.getTotalElements()).isEqualTo(boardList.size());
  }

  @Test
  void 필터조건으로_게시물_조회_성공() {
    // given
    Pageable pageable = Pageable.ofSize(10);
    List<Board> boardList = createBoards();
    Page<Board> boards = new PageImpl<>(boardList, pageable, boardList.size());
    BoardFilterDto filterDto = BoardFilterDto.builder().category(BoardCategory.STUDY).status(BoardStatus.RECRUITING).build();
    when(boardRepository.findByFilter(eq(filterDto), eq(pageable))).thenReturn(boards);

    // when
    Page<BoardResponseDto> result = boardQueryService.getBoardsByFilter(BoardCategory.STUDY,BoardStatus.RECRUITING, null, null, pageable);

    // then
    verify(boardRepository).findByFilter(eq(filterDto), eq(pageable));
    assertThat(result.getTotalElements()).isEqualTo(boardList.size());
  }

  @Test
  void 이번주_인기_게시물_조회_성공() {
    // given
    LocalDateTime startOfWeek = LocalDateTime.now().with(DayOfWeek.MONDAY).toLocalDate().atStartOfDay();
    LocalDateTime endOfWeek = LocalDateTime.now().with(DayOfWeek.SUNDAY).toLocalDate().atTime(23, 59, 59);
    List<Board> popularBoards = createBoards();

    when(boardRepository.findTop10PopularBoardsThisWeek(startOfWeek, endOfWeek)).thenReturn(popularBoards);

    // when
    List<BoardResponseDto> result = boardQueryService.getTop10PopularBoardsThisWeek();

    // then
    verify(boardRepository).findTop10PopularBoardsThisWeek(startOfWeek, endOfWeek);
    assertThat(result.size()).isEqualTo(popularBoards.size());
  }

  @Test
  void 사용자_관심분야_기준_게시물_조회_성공() {
    // given
    Long memberId = 1L;
    List<TechStack> techStacks = createTechStacks();
    List<Board> boards = createBoards();

    when(memberQueryService.getTechStacksByMemberId(memberId)).thenReturn(techStacks);
    when(boardRepository.findAllByTechStacks(techStacks)).thenReturn(boards);

    // when
    List<BoardResponseDto> result = boardQueryService.getBoardsByMemberInterests(memberId);

    // then
    verify(memberQueryService).getTechStacksByMemberId(memberId);
    verify(boardRepository).findAllByTechStacks(techStacks);
    assertThat(result.size()).isEqualTo(boards.size());
  }

  private List<Board> createBoards(){
    List<Board> boardList = new ArrayList<>();
    for(int i=0; i<5; i++){
      boardList.add(BoardFixture.createBoard(mock(Member.class)));
    }
    return boardList;
  }

  private List<TechStack> createTechStacks() {
    List<TechStack> techStacks = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      techStacks.add(mock(TechStack.class));
    }
    return techStacks;
  }
}
