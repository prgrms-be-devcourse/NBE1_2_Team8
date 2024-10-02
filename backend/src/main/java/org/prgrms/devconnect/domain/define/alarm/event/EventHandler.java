package org.prgrms.devconnect.domain.define.alarm.event;

import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.service.alarm.AlarmCommandService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventHandler {

  private final AlarmCommandService alarmService;

  @EventListener
  public void sendWelcomeMessage(RegisteredWelcomeEvent event) {
    alarmService.createWelcomeAlarmWhenSignIn(event.getMember());
    // TODO: 이메일 전송 메서드
  }

}
