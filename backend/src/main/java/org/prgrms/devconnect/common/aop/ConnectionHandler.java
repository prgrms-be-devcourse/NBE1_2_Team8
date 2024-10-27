package org.prgrms.devconnect.common.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ConnectionHandler implements InvocationHandler {

  private final Object target;
  private final QueryCounter counter;

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    countPrepareStatement(method);
    logQueryCount(method);
    return method.invoke(target, args);
  }

  private void logQueryCount(Method method) {
    if (method.getName().equals("close")) {
      log.info("====== 발생한 쿼리 수 : {} =======", counter.getCount());
    }
  }

  private void countPrepareStatement(Method method) {
    if (method.getName().equals("prepareStatement")) {
      counter.increase();
    }
  }

}
