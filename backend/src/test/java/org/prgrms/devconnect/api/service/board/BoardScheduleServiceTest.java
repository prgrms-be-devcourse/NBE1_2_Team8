package org.prgrms.devconnect.api.service.board;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.devconnect.domain.define.board.entity.Board;
import org.prgrms.devconnect.domain.define.board.entity.constant.BoardStatus;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BoardScheduleServiceTest {

  @InjectMocks
  private BoardScheduleService boardScheduleService;

  @Mock
  private BoardQueryService boardQueryService;

  @Mock
  private Board board1;

  @Mock
  private Board board2;

  @Test
  void 만료된_게시물_자동_마감_성공() {
    //given
    List<Board>expiredBoards=List.of(board1,board2);
    when(boardQueryService.findAllByEndDateAndStatus()).thenReturn(expiredBoards);

    //when
    boardScheduleService.closeExpiredBoardAutomatically();

    //then
    verify(board1).changeStatus(BoardStatus.CLOSED);
    verify(board2).changeStatus(BoardStatus.CLOSED);
  }

}
