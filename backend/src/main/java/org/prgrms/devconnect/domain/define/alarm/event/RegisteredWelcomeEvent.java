package org.prgrms.devconnect.domain.define.alarm.event;

import lombok.Getter;
import org.prgrms.devconnect.domain.define.member.entity.Member;

@Getter
public class RegisteredWelcomeEvent {

  private Member member;

  public RegisteredWelcomeEvent(Member member) {
    this.member = member;
  }
}
