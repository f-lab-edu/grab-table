package com.onezerokang.grabtable.global.error.exception;

import com.onezerokang.grabtable.global.error.ApiException;
import org.springframework.http.HttpStatus;

public class InvalidRequestBodyException extends ApiException {
    public InvalidRequestBodyException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public InvalidRequestBodyException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
