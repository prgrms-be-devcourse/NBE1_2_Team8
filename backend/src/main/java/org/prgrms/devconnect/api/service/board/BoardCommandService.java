package org.prgrms.devconnect.api.service.board;

import jakarta.persistence.EntityManager;
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
import org.prgrms.devconnect.domain.define.board.repository.BoardTechStackMappingRepository;
import org.prgrms.devconnect.domain.define.jobpost.entity.JobPost;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.prgrms.devconnect.domain.define.techstack.entity.TechStack;
import org.prgrms.devconnect.domain.define.techstack.repository.TechStackRepository;
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
  private final BoardTechStackMappingRepository boardTechStackMappingRepository;
  private final TechStackRepository techStackRepository;
  private final EntityManager em;

  public Long createBoard(BoardCreateRequestDto boardCreateRequestDto, Long memberId) {
    Member member = memberQueryService.getMemberByIdOrThrow(memberId);

    JobPost jobPost =null;
    if(boardCreateRequestDto.jobPostId()!=null){
      jobPost=jobPostQueryService.getJobPostByIdOrThrow(boardCreateRequestDto.jobPostId());
    }

    List<BoardTechStackMapping> boardTechStackMappings = createBoardTechStackMappings(boardCreateRequestDto);

    Board board = boardCreateRequestDto.toEntity(member, jobPost, boardTechStackMappings);
    boardRepository.save(board);

    return board.getBoardId();
  }

  public Long updateBoard(Long boardId, BoardUpdateRequestDto requestDto) {
    Board board = boardQueryService.getBoardByIdOrThrow(boardId);

    if (board.isDeleted()) {
      throw new BoardException(ExceptionCode.ALREADY_DELETED_BOARD);
    }
    board.updateFromDto(requestDto);

    // 삭제할 TechStack 처리
    List<Long> deleteTechIds = requestDto.deleteTechStacks();
    if (deleteTechIds != null){
      deleteTechStacksFromBoard(board, deleteTechIds);
    }

    // 추가할 TechStack 처리
    List<Long> addTechIds = requestDto.addTechStacks();
    if (addTechIds != null){
      addTechStacksFromBoard(board, addTechIds);
    }

    // 1차 캐시 초기화
    em.flush();
    em.clear();

    return board.getBoardId();
  }

  // 기술 스택 삭제 메서드
  private void deleteTechStacksFromBoard(Board board, List<Long> deleteTechIds) {
    List<Long> idsToDelete = boardTechStackMappingRepository
            .findAllByBoard_BoardIdAndTechStack_TechStackIdIn(board.getBoardId(), deleteTechIds)
            .stream()
            .map(BoardTechStackMapping::getId)
            .collect(Collectors.toList());

    boardTechStackMappingRepository.deleteAllByIds(idsToDelete);
  }

  // 기술 스택 추가 메서드
  private void addTechStacksFromBoard(Board board, List<Long> addTechIds) {
    List<TechStack> techStacks = techStackRepository.findAllByTechStackIdIn(addTechIds);

    if(!techStacks.isEmpty()){
      List<BoardTechStackMapping> mappingToSave = techStacks.stream()
              .map(techStack -> BoardTechStackMapping.builder()
                      .techStack(techStack)
                      .build()
              ).collect(Collectors.toList());

      mappingToSave.forEach(boardTechStackMapping -> boardTechStackMapping.assignBoard(board));

      boardTechStackMappingRepository.saveAll(mappingToSave);
    }
  }

  public void deleteBoard(Long boardId) {
    Board board = boardQueryService.getBoardByIdOrThrow(boardId);
    board.changeStatus(BoardStatus.DELETED);
  }

  public void closeBoardManually(Long boardId) {
    Board board = boardQueryService.getBoardByIdOrThrow(boardId);
    if (board.isClosed()) {
      throw new BoardException(ExceptionCode.ALREADY_CLOSED_BOARD);
    }
    if (board.isDeleted()) {
      throw new BoardException(ExceptionCode.NOT_FOUND_BOARD);
    }
    board.changeStatus(BoardStatus.CLOSED);
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

  // 조회수 증가 로직
  public void increaseViews(Long boardId) {
    boardRepository.incrementViews(boardId);
  }
}
