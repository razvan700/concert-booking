package com.example.concertbookingapplication.exception;

public abstract class ApplicationException extends RuntimeException {

    private final int errorCode;

    protected ApplicationException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
