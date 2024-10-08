package org.prgrms.devconnect.common.exception.refresh;

import org.prgrms.devconnect.common.exception.DevConnectException;
import org.prgrms.devconnect.common.exception.ExceptionCode;

public class RefreshTokenException extends DevConnectException {

  public RefreshTokenException(ExceptionCode exceptionCode) {
    super(exceptionCode);
  }
}
