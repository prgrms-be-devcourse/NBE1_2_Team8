package org.prgrms.devconnect.api.service.board;

import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.board.dto.request.BoardCreateRequestDto;
import org.prgrms.devconnect.common.exception.ExceptionCode;
import org.prgrms.devconnect.common.exception.board.BoardException;
import org.prgrms.devconnect.common.exception.member.MemberException;
import org.prgrms.devconnect.domain.define.board.entity.Board;
import org.prgrms.devconnect.domain.define.board.entity.BoardTechStackMapping;
import org.prgrms.devconnect.domain.define.board.entity.constant.BoardStatus;
import org.prgrms.devconnect.domain.define.board.repository.BoardRepository;
import org.prgrms.devconnect.domain.define.jobpost.entity.JobPost;
import org.prgrms.devconnect.domain.define.jobpost.repository.JobPostRepository;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.prgrms.devconnect.domain.define.member.repository.MemberRepository;
import org.prgrms.devconnect.domain.define.techstack.entity.TechStack;
import org.prgrms.devconnect.domain.define.techstack.repository.TechStackRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class BoardService {

  private final BoardRepository boardRepository;

  private final MemberRepository memberRepository;

  private final JobPostRepository jobPostRepository;

  private final TechStackRepository techStackRepository;


  @Transactional
  public Long createBoard(BoardCreateRequestDto boardCreateRequestDto) {
    Member member = memberRepository.findById(boardCreateRequestDto.memberId())
            .orElseThrow(()->new MemberException(ExceptionCode.NOT_FOUND_MEMBER));

    // JobPost 처리: null일 경우 일반 게시판으로 처리하고, null이 아닌데 조회 실패하면 예외 처리
    JobPost jobPost = null;
    if (boardCreateRequestDto.jobPostId() != null) {
      jobPost = jobPostRepository.findById(boardCreateRequestDto.jobPostId())
              .orElseThrow(() -> new BoardException(ExceptionCode.NOT_FOUND_JOB_POST));
    }

    List<BoardTechStackMapping> boardTechStackMappings = createBoardTechStackMappings(boardCreateRequestDto);

    Board board = boardCreateRequestDto.toEntity(member, jobPost, boardTechStackMappings);
    boardRepository.save(board);

    return board.getBoardId();
  }

  @Transactional
  public Long updateBoard(Long boardId, BoardCreateRequestDto boardCreateRequestDto) {
    Board board = boardRepository.findById(boardId)
            .orElseThrow(() -> new BoardException(ExceptionCode.NOT_FOUND_BOARD));

    board.updateBoardInfo(
            boardCreateRequestDto.title(),
            boardCreateRequestDto.content(),
            boardCreateRequestDto.category(),
            boardCreateRequestDto.recruitNum(),
            boardCreateRequestDto.progressWay(),
            boardCreateRequestDto.progressPeriod(),
            boardCreateRequestDto.endDate()
    );
    List<BoardTechStackMapping> boardTechStackMappings = createBoardTechStackMappings(boardCreateRequestDto);
    board.getBoardTechStacks().clear();  // 기존 매핑 제거
    boardTechStackMappings.forEach(board::addTechStack);  // 새 매핑 추가

    return  board.getBoardId();
  }

  @Transactional
  public void deleteBoard(Long boardId) {
    Board board = boardRepository.findById(boardId)
            .orElseThrow(() -> new BoardException(ExceptionCode.NOT_FOUND_BOARD));
    board.changeStatus(BoardStatus.DELETED);
  }



  private List<BoardTechStackMapping> createBoardTechStackMappings(BoardCreateRequestDto boardCreateRequestDto) {
    return boardCreateRequestDto.techStackRequests().stream()
            .map(requestdto -> {
              TechStack techStack = techStackRepository.findById(requestdto.techStackId())
                      .orElseThrow(() -> new BoardException(ExceptionCode.NOT_FOUND_TECH_STACK));
              return requestdto.toEntity(techStack);
            })
            .collect(Collectors.toList());
  }
}
