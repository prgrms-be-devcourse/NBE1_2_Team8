package org.prgrms.devconnect.common.exception;

public record ExceptionResponse(
        int code,
        String message
) {
}
