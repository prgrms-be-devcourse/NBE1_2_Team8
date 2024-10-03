package org.prgrms.devconnect.api.controller.interest.dto.request;

import lombok.Builder;
import org.prgrms.devconnect.domain.define.board.entity.Board;
import org.prgrms.devconnect.domain.define.interest.entity.InterestBoard;
import org.prgrms.devconnect.domain.define.member.entity.Member;

@Builder
public record InterestBoardRequestDto(
    Long memberId,
    Long boardId
) {

  public InterestBoard toEntity(Member member, Board board) {
    return InterestBoard.builder()
        .member(member)
        .board(board)
        .build();
  }
}
