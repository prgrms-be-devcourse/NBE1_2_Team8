package org.prgrms.devconnect.common.exception.jwt;

import org.prgrms.devconnect.common.exception.DevConnectException;
import org.prgrms.devconnect.common.exception.ExceptionCode;

public class JwtException extends DevConnectException {

  public JwtException(ExceptionCode exceptionCode) {
    super(exceptionCode);
  }
}
