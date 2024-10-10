package org.prgrms.devconnect.domain.define.board.entity.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BoardStatus {
  RECRUITING("모집중"),
  CLOSED("마감"),
  DELETED("삭제된 게시글");

  private final String text;
}
