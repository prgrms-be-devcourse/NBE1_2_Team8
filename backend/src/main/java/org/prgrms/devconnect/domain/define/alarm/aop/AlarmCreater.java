package org.prgrms.devconnect.domain.define.alarm.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.prgrms.devconnect.domain.define.alarm.event.RegisteredWelcomeEvent;
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
    if (object instanceof Member) {
      publisher.publishEvent(new RegisteredWelcomeEvent((Member) object));
    }
    return object;
  }
}
