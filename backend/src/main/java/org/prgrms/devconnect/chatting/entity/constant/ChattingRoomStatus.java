package org.prgrms.devconnect.chatting.entity.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ChattingRoomStatus {
  ACTIVE("활성화"),
  INACTIVE("비활성화");

  private final String text;
}
