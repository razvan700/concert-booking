package com.example.concertbookingapplication.advice;

import com.example.concertbookingapplication.error.ErrorDto;
import com.example.concertbookingapplication.exception.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
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

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<ErrorDto> handleOptimisticLock(
            ObjectOptimisticLockingFailureException ex) {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorDto(
                        "Resource was modified by another transaction",
                        409,
                        "OPTIMISTIC_LOCK_CONFLICT"
                ));
    }
}
