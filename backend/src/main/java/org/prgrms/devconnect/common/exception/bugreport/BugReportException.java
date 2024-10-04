package org.prgrms.devconnect.common.exception.bugreport;

import org.prgrms.devconnect.common.exception.DevConnectException;
import org.prgrms.devconnect.common.exception.ExceptionCode;

public class BugReportException extends DevConnectException {
  public BugReportException(ExceptionCode exceptionCode) {
    super(exceptionCode);
  }
}
