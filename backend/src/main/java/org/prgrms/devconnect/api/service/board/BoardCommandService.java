package org.prgrms.devconnect.api.service.board;

import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.board.dto.request.BoardCreateRequestDto;
import org.prgrms.devconnect.api.controller.board.dto.request.BoardTechStackRequestDto;
import org.prgrms.devconnect.api.controller.board.dto.request.BoardUpdateRequestDto;
import org.prgrms.devconnect.api.service.jobpost.JobPostQueryService;
import org.prgrms.devconnect.api.service.member.MemberQueryService;
import org.prgrms.devconnect.api.service.techstack.TechStackQueryService;
import org.prgrms.devconnect.common.exception.ExceptionCode;
import org.prgrms.devconnect.common.exception.board.BoardException;
import org.prgrms.devconnect.domain.define.board.entity.Board;
import org.prgrms.devconnect.domain.define.board.entity.BoardTechStackMapping;
import org.prgrms.devconnect.domain.define.board.entity.constant.BoardStatus;
import org.prgrms.devconnect.domain.define.board.repository.BoardRepository;
import org.prgrms.devconnect.domain.define.jobpost.entity.JobPost;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.prgrms.devconnect.domain.define.techstack.entity.TechStack;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardCommandService {
  private final BoardRepository boardRepository;
  private final MemberQueryService memberQueryService;
  private final JobPostQueryService jobPostQueryService;
  private final TechStackQueryService techStackQueryService;
  private final BoardQueryService boardQueryService;

  public Long createBoard(BoardCreateRequestDto boardCreateRequestDto) {
    Member member = memberQueryService.getMemberByIdOrThrow(boardCreateRequestDto.memberId());

    JobPost jobPost =null;
    if(boardCreateRequestDto.jobPostId()!=null){
      jobPost=jobPostQueryService.getJobPostByIdOrThrow(boardCreateRequestDto.jobPostId());
    }

    List<BoardTechStackMapping> boardTechStackMappings = createBoardTechStackMappings(boardCreateRequestDto);

    Board board = boardCreateRequestDto.toEntity(member, jobPost, boardTechStackMappings);
    boardRepository.save(board);

    return board.getBoardId();
  }

//  게시물 수정은 추후 구현 예정
  public Long updateBoard(Long boardId, BoardUpdateRequestDto requestDto) {
    Board board = boardQueryService.getBoardByIdOrThrow(boardId);

    if (board.getStatus() == BoardStatus.DELETED) {
      throw new BoardException(ExceptionCode.NOT_FOUND_BOARD);
    }

    board.updateFromDto(requestDto);
    return board.getBoardId();
  }

  public void deleteBoard(Long boardId) {
    Board board = boardQueryService.getBoardByIdOrThrow(boardId);
    board.changeStatus(BoardStatus.DELETED);
  }

  public void closeBoardManually(Long boardId) {
    Board board = boardQueryService.getBoardByIdOrThrow(boardId);
    if (board.getStatus() == BoardStatus.CLOSED ) {
      throw new BoardException(ExceptionCode.ALREADY_CLOSED_BOARD);
    }
    if (board.getStatus() == BoardStatus.DELETED) {
      throw new BoardException(ExceptionCode.NOT_FOUND_BOARD);
    }
    board.changeStatus(BoardStatus.CLOSED);
  }

  @Scheduled(cron = "0 0 0 * * *")
  public void scheduleAutoClose() {
    closeExpiredBoardAutomatically();
  }

  public void closeExpiredBoardAutomatically() {
    List<Board> expiredBoards = boardQueryService.findAllByEndDateAndStatus();
    for (Board board : expiredBoards) {
      board.changeStatus(BoardStatus.CLOSED);
    }
  }

  private List<BoardTechStackMapping> createBoardTechStackMappings(BoardCreateRequestDto boardCreateRequestDto) {
    List<Long> techStackIds = boardCreateRequestDto.techStackRequests().stream()
            .map(BoardTechStackRequestDto::techStackId)
            .collect(Collectors.toList());

    List<TechStack> techStacks = techStackQueryService.getTechStacksByIdsOrThrow(techStackIds);

    return techStacks.stream()
            .map(techStack -> {
              // 각각의 TechStack에 대해 BoardTechStackMapping 생성
              return BoardTechStackMapping.builder()
                      .techStack(techStack)
                      .build();
            })
            .collect(Collectors.toList());
  }
}
