package org.prgrms.devconnect.common.exception.alarm;

import org.prgrms.devconnect.common.exception.DevConnectException;
import org.prgrms.devconnect.common.exception.ExceptionCode;

public class AlarmException extends DevConnectException {
  public AlarmException(ExceptionCode exceptionCode) {
    super(exceptionCode);
  }
}
