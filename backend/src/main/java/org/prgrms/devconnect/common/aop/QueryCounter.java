package org.prgrms.devconnect.common.aop;

import lombok.Getter;

@Getter
public class QueryCounter {

  private int count;

  public void increase() {
    count++;
  }

}