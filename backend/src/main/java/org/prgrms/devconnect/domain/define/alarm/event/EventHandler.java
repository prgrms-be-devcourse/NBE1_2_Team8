package org.prgrms.devconnect.domain.define.alarm.event;

import lombok.RequiredArgsConstructor;
import org.prgrms.devconnect.api.service.alarm.AlarmCommandService;
import org.prgrms.devconnect.api.service.alarm.EmailService;
import org.prgrms.devconnect.domain.define.alarm.entity.Alarm;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventHandler {

  private final AlarmCommandService alarmService;
  private final EmailService emailService;

  @EventListener
  public void sendWelcomeMessage(RegisteredWelcomeEvent event) {
    Alarm alarm = alarmService.createWelcomeAlarmWhenSignIn(event.member());
    emailService.sendEmail(alarm);
  }

  @EventListener
  public void sendCommentPostedOnBoardToBoardPoster(RegisteredCommentOnBoardEvent event) {
    Alarm alarm = alarmService.createCommentPostedMessageToBoardPoster(event.comment());
    emailService.sendEmail(alarm);
  }

  @EventListener
  public void sendRegisteredReplyCommentMessageToParentCommenter(RegisteredReplyCommentEvent event) {
    Alarm alarm = alarmService.createReplyCommentReceivedAlarmToParentCommenter(event.comment());
    emailService.sendEmail(alarm);
  }

  @EventListener
  public void sendBoardUrgentAlarm(RegisteredUrgentEvent event) {
    Alarm alarm = alarmService.createUrgentAlarmAboutInterestBoard(event.interestBoard());
    emailService.sendEmail(alarm);
  }
}
