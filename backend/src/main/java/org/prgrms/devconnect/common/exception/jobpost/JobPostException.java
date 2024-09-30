package org.prgrms.devconnect.common.exception.jobpost;

import org.prgrms.devconnect.common.exception.DevConnectException;
import org.prgrms.devconnect.common.exception.ExceptionCode;

public class JobPostException extends DevConnectException {
  public JobPostException(ExceptionCode exceptionCode) {
    super(exceptionCode);
  }
}
