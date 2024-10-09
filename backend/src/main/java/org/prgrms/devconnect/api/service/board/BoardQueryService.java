package org.prgrms.devconnect.api.service.board;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.board.dto.BoardFilterDto;
import org.prgrms.devconnect.api.controller.board.dto.response.BoardResponseDto;
import org.prgrms.devconnect.api.service.jobpost.JobPostQueryService;
import org.prgrms.devconnect.api.service.member.MemberQueryService;
import org.prgrms.devconnect.common.exception.ExceptionCode;
import org.prgrms.devconnect.common.exception.board.BoardException;
import org.prgrms.devconnect.domain.define.board.entity.Board;
import org.prgrms.devconnect.domain.define.board.entity.constant.BoardCategory;
import org.prgrms.devconnect.domain.define.board.entity.constant.BoardStatus;
import org.prgrms.devconnect.domain.define.board.entity.constant.ProgressWay;
import org.prgrms.devconnect.domain.define.board.repository.BoardRepository;
import org.prgrms.devconnect.domain.define.techstack.entity.TechStack;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardQueryService {

  private final BoardRepository boardRepository;
  private final MemberQueryService memberQueryService;
  private final JobPostQueryService jobPostQueryService;

  public Board getBoardByIdOrThrow(Long boardId) {
    return boardRepository.findByIdAndStatusNotDeleted(boardId)
        .orElseThrow(() -> new BoardException(ExceptionCode.NOT_FOUND_BOARD));
  }

  // 게시물을 조회하면서 조회수 증가도 처리
  public BoardResponseDto getBoardById(Long boardId) {
    //게시물 조회
    Board board = getBoardByIdOrThrow(boardId);

    //게시물 조회수 증가
    board.increaseViews();
    return BoardResponseDto.from(board);
  }

  public Page<BoardResponseDto> getAllBoards(Pageable pageable) {
    Page<Board> boards = boardRepository.findAllWithTechStackByStatusNotDeleted(pageable);
    return boards.map(BoardResponseDto::from);
  }

  public List<Board> findAllByEndDateAndStatus() {
    return boardRepository.findAllByEndDateAndStatus(LocalDateTime.now(), BoardStatus.RECRUITING);
  }

  public Page<BoardResponseDto> getBoardsByFilter(BoardCategory category, BoardStatus status,
      List<Long> techStackIds, ProgressWay progressWay, Pageable pageable) {
    BoardFilterDto filterDto = BoardFilterDto.builder()
        .category(category)
        .status(status)
        .techStackIds(techStackIds)
        .progressWay(progressWay)
        .build();
    if (filterDto.isEmpty()) {
      return getAllBoards(pageable);
    }
    Page<Board> filteredBoards = boardRepository.findByFilter(filterDto, pageable);
    return filteredBoards.map(BoardResponseDto::from);
  }

  public List<BoardResponseDto> getTop10PopularBoardsThisWeek() {
    LocalDateTime startOfWeek = LocalDateTime.now().with(DayOfWeek.MONDAY).toLocalDate()
        .atStartOfDay();
    LocalDateTime endOfWeek = LocalDateTime.now().with(DayOfWeek.SUNDAY).toLocalDate()
        .atTime(23, 59, 59);
    List<Board> boards = boardRepository.findTop10PopularBoardsThisWeek(startOfWeek, endOfWeek);
    return boards.stream()
        .map(BoardResponseDto::from)
        .collect(Collectors.toList());
  }

  public List<BoardResponseDto> getBoardsByMemberInterests(Long memberId) {
    List<TechStack> memberTechStacks = memberQueryService.getTechStacksByMemberId(memberId);
    List<Board> boards = boardRepository.findAllByTechStacks(memberTechStacks);
    return boards.stream().map(BoardResponseDto::from).collect(Collectors.toList());
  }

  public List<BoardResponseDto> getBoardsByJobPostId(Long jobPostId) {
    jobPostQueryService.getJobPostByIdOrThrow(jobPostId);
    List<Board> boards = boardRepository.findAllByJobPostId(jobPostId);
    return boards.stream()
        .map(BoardResponseDto::from)
        .collect(Collectors.toList());
  }

  public List<BoardResponseDto> getBoardsWithPopularTagCondition() {
    LocalDateTime startOfWeek = LocalDateTime.now().with(DayOfWeek.MONDAY).toLocalDate()
        .atStartOfDay();
    LocalDateTime endOfWeek = LocalDateTime.now().with(DayOfWeek.SUNDAY).toLocalDate()
        .atTime(23, 59, 59);
    List<Board> boards = boardRepository.findBoardsWithPopularTagCondition(startOfWeek, endOfWeek);
    return boards.stream()
        .map(BoardResponseDto::from)
        .collect(Collectors.toList());
  }

  public List<BoardResponseDto> getBoardsWithDeadlineApproaching() {
    LocalDateTime currentDate = LocalDateTime.now();
    LocalDateTime deadlineDate = currentDate.plusDays(2);
    List<Board> boards = boardRepository.findBoardsWithDeadlineApproaching(currentDate,
        deadlineDate);
    return boards.stream()
        .map(BoardResponseDto::from)
        .collect(Collectors.toList());
  }
}

