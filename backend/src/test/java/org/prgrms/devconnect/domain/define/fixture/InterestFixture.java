package org.prgrms.devconnect.domain.define.fixture;

import org.prgrms.devconnect.api.controller.interest.dto.request.InterestBoardRequestDto;
import org.prgrms.devconnect.domain.define.board.entity.Board;
import org.prgrms.devconnect.domain.define.interest.entity.InterestBoard;
import org.prgrms.devconnect.domain.define.member.entity.Member;

public class InterestFixture {

  // InterestBoard
  public static InterestBoard createInterestBoard(Member member, Board board) {
    return InterestBoard.builder()
        .member(member)
        .board(board)
        .build();
  }

  // InterestBoardRequestDto
  public static InterestBoardRequestDto createInterestBoardRequestDto() {
    return InterestBoardRequestDto.builder()
        .memberId(1L)
        .boardId(1L)
        .build();

  }

  public static InterestBoardRequestDto createInterestBoardRequestDto(Long memberId, Long boardId) {
    return InterestBoardRequestDto.builder()
        .memberId(memberId)
        .boardId(boardId)
        .build();

  }

}
