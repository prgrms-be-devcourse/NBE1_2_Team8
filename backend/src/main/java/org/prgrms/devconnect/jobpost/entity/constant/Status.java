package org.prgrms.devconnect.jobpost.entity.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum Status {

  RECRUITING("모집중"),
  DEADLINE("마감"),
  DELETED("삭제됨");

  private final String text;
}
