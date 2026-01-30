package com.example.concertbookingapplication.advice;

import com.example.concertbookingapplication.error.ErrorDto;
import com.example.concertbookingapplication.exception.ApplicationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorDto> handleAppException(ApplicationException ex) {

        ErrorDto errorDto = new ErrorDto(
                ex.getMessage(),
                ex.getHttpStatus().value(),
                ex.getErrorCode()
        );

        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(errorDto);
    }
}
