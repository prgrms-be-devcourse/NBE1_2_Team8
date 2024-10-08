package org.prgrms.devconnect.common.exception.alarm;

import org.prgrms.devconnect.common.exception.DevConnectException;
import org.prgrms.devconnect.common.exception.ExceptionCode;

public class EmailException extends DevConnectException {

  public EmailException(ExceptionCode exceptionCode) {
    super(exceptionCode);
  }
}
