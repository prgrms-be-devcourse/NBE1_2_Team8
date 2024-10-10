package org.prgrms.devconnect.common.exception.chatting;

import org.prgrms.devconnect.common.exception.DevConnectException;
import org.prgrms.devconnect.common.exception.ExceptionCode;

public class ChattingException extends DevConnectException {
  public ChattingException(ExceptionCode exceptionCode) {
    super(exceptionCode);
  }
}
