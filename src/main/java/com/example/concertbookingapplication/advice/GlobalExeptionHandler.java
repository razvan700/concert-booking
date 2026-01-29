package com.example.concertbookingapplication.advice;

import com.example.concertbookingapplication.error.ErrorResponse;
import com.example.concertbookingapplication.exception.ApplicationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExeptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> handleAppException(ApplicationException ex) {

        return ResponseEntity
                .status(ex.getErrorCode())
                .body(new ErrorResponse(
                        ex.getErrorCode(),
                        ex.getMessage()
                ));
    }
}
