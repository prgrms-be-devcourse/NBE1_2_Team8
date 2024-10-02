package org.prgrms.devconnect.common.exception.comment;

import org.prgrms.devconnect.common.exception.DevConnectException;
import org.prgrms.devconnect.common.exception.ExceptionCode;

public class CommentException extends DevConnectException {
  public CommentException(ExceptionCode exceptionCode){
    super(exceptionCode);
  }
}

