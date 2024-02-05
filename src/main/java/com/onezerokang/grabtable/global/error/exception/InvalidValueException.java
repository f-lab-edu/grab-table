package com.onezerokang.grabtable.global.error.exception;

import com.onezerokang.grabtable.global.error.ApiException;
import org.springframework.http.HttpStatus;

public class InvalidValueException extends ApiException {
    public InvalidValueException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public InvalidValueException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
