package org.prgrms.devconnect.api.service.board;

import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.common.exception.ExceptionCode;
import org.prgrms.devconnect.common.exception.board.BoardException;
import org.prgrms.devconnect.domain.define.board.entity.Board;
import org.prgrms.devconnect.domain.define.board.entity.constant.BoardStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardScheduleService {
  private final BoardQueryService boardQueryService;

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
}
