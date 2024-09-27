package org.prgrms.devconnect.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(BusinessLogicException.class)
  public ResponseEntity<ExceptionResponse> handlerBadRequestException(BusinessLogicException e) {

    log.warn(e.getMessage(), e);

    return ResponseEntity
            .status(e.getExceptionCode().getCode())
            .body(new ExceptionResponse(e.getExceptionCode().getCode(), e.getMessage()));
  }
}
