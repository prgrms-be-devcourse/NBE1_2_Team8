package org.prgrms.devconnect.domain.define.alarm.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.prgrms.devconnect.domain.define.alarm.event.RegisteredCommentOnBoardEvent;
import org.prgrms.devconnect.domain.define.alarm.event.RegisteredReplyCommentEvent;
import org.prgrms.devconnect.domain.define.alarm.event.RegisteredWelcomeEvent;
import org.prgrms.devconnect.domain.define.board.entity.Comment;
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

    if ("createMember".equals(methodName)) {
      publisher.publishEvent(new RegisteredWelcomeEvent((Member) object));
    }

    if ("createComment".equals(methodName)) {
      Comment comment = (Comment) object;
      if(comment.getParent().isRootComment()) {
        publisher.publishEvent(new RegisteredCommentOnBoardEvent(comment));
      }
      publisher.publishEvent(new RegisteredReplyCommentEvent(comment));
    }
    return object;
  }
}
