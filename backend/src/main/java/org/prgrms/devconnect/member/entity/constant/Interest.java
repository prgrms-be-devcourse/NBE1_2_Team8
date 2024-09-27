package org.prgrms.devconnect.member.entity.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Interest {

  STUDY("스터디"),
  TEAM_PROJECT("팀 프로젝트"),
  JOBPOST("채용 공고");

  private final String text;
}
