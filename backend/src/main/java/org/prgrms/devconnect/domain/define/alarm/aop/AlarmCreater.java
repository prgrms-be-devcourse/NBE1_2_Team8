package org.prgrms.devconnect.domain.define.alarm.aop;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.prgrms.devconnect.api.controller.chatting.dto.response.MessageResponse;
import org.prgrms.devconnect.domain.define.alarm.event.RegisteredCommentOnBoardEvent;
import org.prgrms.devconnect.domain.define.alarm.event.RegisteredReceivedMessageEvent;
import org.prgrms.devconnect.domain.define.alarm.event.RegisteredReplyCommentEvent;
import org.prgrms.devconnect.domain.define.alarm.event.RegisteredUrgentEvent;
import org.prgrms.devconnect.domain.define.alarm.event.RegisteredWelcomeEvent;
import org.prgrms.devconnect.domain.define.board.entity.Comment;
import org.prgrms.devconnect.domain.define.interest.entity.InterestBoard;
import org.prgrms.devconnect.domain.define.member.entity.Member;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class AlarmCreater {

  private final ApplicationEventPublisher publisher;

  @Around("@annotation(org.prgrms.devconnect.domain.define.alarm.aop.RegisterPublisher)")
  public Object processRegisterPublisherAnnotation(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

    Object object = proceedingJoinPoint.proceed();
    String methodName = proceedingJoinPoint.getSignature().getName();

    // TODO: 팩토리 메서드 패턴으로 리펙토링
    if ("sendMessage".equals(methodName)) {
      publisher.publishEvent(new RegisteredReceivedMessageEvent((MessageResponse) object));
    } else if ("findAllUrgentBoards".equals(methodName)) {
      for (InterestBoard interestBoard : (List<InterestBoard>) object) {
        publisher.publishEvent(new RegisteredUrgentEvent(interestBoard));
      }
    } else if ("createComment".equals(methodName)) {
      Comment comment = (Comment) object;
      publisher.publishEvent(new RegisteredCommentOnBoardEvent(comment));
      if (comment.getParent() != null) {
        publisher.publishEvent(new RegisteredReplyCommentEvent(comment));
      }
    } else if ("createMember".equals(methodName)) {
      publisher.publishEvent(new RegisteredWelcomeEvent((Member) object));
    }

    return object;
  }
}
