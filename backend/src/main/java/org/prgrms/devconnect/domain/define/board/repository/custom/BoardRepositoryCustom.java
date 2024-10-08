package org.prgrms.devconnect.domain.define.board.repository.custom;

import org.prgrms.devconnect.api.controller.board.dto.BoardFilterDto;
import org.prgrms.devconnect.domain.define.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface BoardRepositoryCustom {
  List<Board> findTop10PopularBoardsThisWeek(LocalDateTime startOfWeek, LocalDateTime endOfWeek);
  Page<Board> findByFilter(BoardFilterDto filterDto, Pageable pageable);
}
