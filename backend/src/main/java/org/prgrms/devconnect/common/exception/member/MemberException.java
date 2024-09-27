package org.prgrms.devconnect.common.exception.member;

import org.prgrms.devconnect.common.exception.DevConnectException;
import org.prgrms.devconnect.common.exception.ExceptionCode;

public class MemberException extends DevConnectException {
  public MemberException(ExceptionCode exceptionCode) {
    super(exceptionCode);
  }
}