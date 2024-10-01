package org.prgrms.devconnect.domain.define.board.entity.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ProgressWay {

  ONLINE("온라인"),
  OFFLINE("오프라인"),
  HYBRID("온/오프라인");

  private final String text;
}
