package org.prgrms.devconnect.common.exception.board;

import org.prgrms.devconnect.common.exception.DevConnectException;
import org.prgrms.devconnect.common.exception.ExceptionCode;

public class BoardException extends DevConnectException {
  public BoardException(ExceptionCode exceptionCode) {
    super(exceptionCode);
  }
}
