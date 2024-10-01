package org.prgrms.devconnect.domain.define.board.entity.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BoardCategory {

  STUDY("스터디"),
  TEAM_PROJECT("팀 프로젝트");

  private final String text;
}
