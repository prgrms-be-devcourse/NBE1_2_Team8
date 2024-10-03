package org.prgrms.devconnect.common.exception.interest;

import org.prgrms.devconnect.common.exception.DevConnectException;
import org.prgrms.devconnect.common.exception.ExceptionCode;

public class InterestException extends DevConnectException {

  public InterestException(ExceptionCode exceptionCode) {
    super(exceptionCode);
  }
}
