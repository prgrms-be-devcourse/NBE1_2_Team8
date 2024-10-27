package org.prgrms.devconnect.common.aop;

import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Slf4j
@Component
public class PerformanceAspect {

  private final ThreadLocal<QueryCounter> queryCounter = new ThreadLocal<>();

  @Pointcut("execution(* javax.sql.DataSource.getConnection(..))")
  public void performancePointcut() {
  }

  @Around("performancePointcut()")
  public Object start (ProceedingJoinPoint point) throws Throwable {
    RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
    if (requestAttributes instanceof ServletRequestAttributes) {
      HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
      String httpMethod = request.getMethod();
      String requestURI = request.getRequestURI();

      log.info("HTTP Method: {}, Request URI: {}", httpMethod, requestURI);
    } else {
      log.info("No active HTTP request context available.");
    }

    final Connection connection = (Connection) point.proceed();
    queryCounter.set(new QueryCounter());
    final QueryCounter counter = this.queryCounter.get();

    final Connection proxyConnection = getProxyConnection(connection, counter);
    queryCounter.remove();
    return proxyConnection;
  }

  private Connection getProxyConnection(Connection connection, QueryCounter counter) {
    return (Connection) Proxy.newProxyInstance(
        getClass().getClassLoader(),
        new Class[]{Connection.class},
        new ConnectionHandler(connection, counter)
    );
  }
}
