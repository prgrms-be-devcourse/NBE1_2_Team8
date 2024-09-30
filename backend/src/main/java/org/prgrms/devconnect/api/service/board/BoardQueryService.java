package org.prgrms.devconnect.api.service.board;

import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.common.exception.ExceptionCode;
import org.prgrms.devconnect.common.exception.board.BoardException;
import org.prgrms.devconnect.domain.define.board.entity.Board;
import org.prgrms.devconnect.domain.define.board.repository.BoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardQueryService {

  private final BoardRepository boardRepository;

  public Board getBoardByIdOrThrow(Long boardId) {
    return boardRepository.findById(boardId)
            .orElseThrow(() -> new BoardException(ExceptionCode.NOT_FOUND_BOARD));
  }
}

