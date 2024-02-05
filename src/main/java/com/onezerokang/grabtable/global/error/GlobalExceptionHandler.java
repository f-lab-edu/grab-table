package com.onezerokang.grabtable.global.error;

import com.onezerokang.grabtable.global.common.ErrorResponse;
import com.onezerokang.grabtable.global.error.exception.InvalidValueException;
import lombok.extern.slf4j.Slf4j;
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
        return ResponseEntity.status(e.getHttpStatus()).body(new ErrorResponse(e.getHttpStatus().value(), e.getMessage(), e.getErrorCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(Exception e) {
        log.error("handleUnexpectedException", e);
        String message = "Internal server error";
        String errorCode = "InternalServerError";
        return ResponseEntity.status(500).body(new ErrorResponse(500, message, errorCode));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException", e);
        InvalidValueException ie = new InvalidValueException("유효하지 않은 Request Body 입니다.");
        List<ValidationError> errors = e.getBindingResult().getFieldErrors().stream().map(ValidationError::of).toList();
        return ResponseEntity.status(e.getStatusCode()).body(new ErrorResponse(ie.getHttpStatus().value(), ie.getMessage(), ie.getErrorCode(), errors));
    }
}