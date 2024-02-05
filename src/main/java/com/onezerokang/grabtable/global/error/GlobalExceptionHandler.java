package com.onezerokang.grabtable.global.error;

import com.onezerokang.grabtable.global.common.ErrorResponse;
import com.onezerokang.grabtable.global.error.exception.InternalServerError;
import com.onezerokang.grabtable.global.error.exception.InvalidValueException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static com.onezerokang.grabtable.global.common.ErrorResponse.*;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException e) {
        log.error("handleApiException", e);
        return ResponseEntity.status(e.getHttpStatus()).body(ErrorResponse.of(e));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(Exception e) {
        log.error("handleUnexpectedException", e);
        InternalServerError ise = new InternalServerError();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.of(ise));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException", e);
        InvalidValueException ie = new InvalidValueException("유효하지 않은 Request Body 입니다.");
        return ResponseEntity.status(e.getStatusCode()).body(ErrorResponse.of(ie, e.getBindingResult()));
    }
}