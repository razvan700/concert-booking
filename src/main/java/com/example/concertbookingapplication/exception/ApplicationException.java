package com.example.concertbookingapplication.exception;

import org.springframework.http.HttpStatus;

public abstract class ApplicationException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String errorCode;

    protected ApplicationException(
            String message,
            HttpStatus httpStatus,
            String errorCode
    ) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
