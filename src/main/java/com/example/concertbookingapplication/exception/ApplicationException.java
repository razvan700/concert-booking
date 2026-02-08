package com.example.concertbookingapplication.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public abstract class ApplicationException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String errorCode;

    public ApplicationException(String s, int i) {
        super(s);
        httpStatus = HttpStatus.valueOf(i);
        errorCode = s;
    }
}
