package org.prgrms.devconnect.bugreport.entity.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BugType {
  NOT_FOUND("유효하지 않는 URL"),
  EXPIRED("만기된 공고"),
  OTHER("기타");

  private final String text;
}
