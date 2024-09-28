package org.prgrms.devconnect.common.exception.techstack;

import org.prgrms.devconnect.common.exception.DevConnectException;
import org.prgrms.devconnect.common.exception.ExceptionCode;

public class TechStackException extends DevConnectException {

  public TechStackException(ExceptionCode exceptionCode) {
    super(exceptionCode);
  }
}
