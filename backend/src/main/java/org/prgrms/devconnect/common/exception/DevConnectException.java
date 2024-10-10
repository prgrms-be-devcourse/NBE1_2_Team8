package org.prgrms.devconnect.common.exception;

import lombok.Getter;

@Getter
public abstract class DevConnectException extends RuntimeException {
  private final ExceptionCode exceptionCode;

  public DevConnectException(ExceptionCode exceptionCode) {
    super(exceptionCode.getMessage());
    this.exceptionCode = exceptionCode;
  }
}
