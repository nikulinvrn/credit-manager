package ru.trainee.creditmanager.listener.handler;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.trainee.creditmanager.dto.ApiError;
import ru.trainee.creditmanager.exception.CreditLimitExceedException;


@ControllerAdvice
public class ExceptionHandlers extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EntityNotFoundException.class})
    protected ResponseEntity<ApiError<?>> notFoundException(Exception exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError<>(HttpStatus.NOT_FOUND, exception.getMessage()));
    }

    @ExceptionHandler({CreditLimitExceedException.class})
    protected ResponseEntity<ApiError<?>> creditLimitExceededException(Exception exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiError<>(HttpStatus.BAD_REQUEST, exception.getMessage()));
    }
}
