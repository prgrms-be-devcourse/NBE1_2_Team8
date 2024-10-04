package org.prgrms.devconnect.api.service.board;

import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.controller.board.dto.response.BoardResponseDto;
import org.prgrms.devconnect.common.exception.ExceptionCode;
import org.prgrms.devconnect.common.exception.board.BoardException;
import org.prgrms.devconnect.domain.define.board.entity.Board;
import org.prgrms.devconnect.domain.define.board.entity.constant.BoardStatus;
import org.prgrms.devconnect.domain.define.board.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardQueryService {

  private final BoardRepository boardRepository;

  public Board getBoardByIdOrThrow(Long boardId) {
    return boardRepository.findByIdAndStatusNotDeleted(boardId)
            .orElseThrow(() -> new BoardException(ExceptionCode.NOT_FOUND_BOARD));
  }

  public BoardResponseDto getBoardById(Long boardId){
    Board board = getBoardByIdOrThrow(boardId);
    return BoardResponseDto.from(board);
  }

  public Page<BoardResponseDto> getAllBoards(Pageable pageable){
    Page<Board>boards=boardRepository.findAllWithTechStackByStatusNotDeleted(pageable);
    return boards.map(BoardResponseDto::from);
  }

  public List<Board> findAllByEndDateAndStatus() {
    return boardRepository.findAllByEndDateAndStatus(LocalDateTime.now(), BoardStatus.RECRUITING);
  }
}

