package com.onezerokang.grabtable.global.error;

import com.onezerokang.grabtable.global.common.ErrorResponse;
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
        List<ValidationError> errors = e.getBindingResult().getFieldErrors().stream().map(ValidationError::of).toList();
        String errorCode = e.getClass().getSimpleName().replace("Exception", "");
        String message = "잘못된 매개변수입니다.";
        return ResponseEntity.status(e.getStatusCode()).body(new ErrorResponse(e.getStatusCode().value(), message, errorCode, errors));
    }
}