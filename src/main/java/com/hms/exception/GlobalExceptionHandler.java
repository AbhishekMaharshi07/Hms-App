package com.hms.exception;


import com.hms.payloads.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDto> resourceNotFound(
            ResourceNotFoundException r,
            WebRequest request
            // What is WebRequest??
    ){
            ErrorDto error = new ErrorDto(
                    r.getMessage(),new Date(),request.getDescription(false));
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(RecordAlreadyExistsException.class)
    public ResponseEntity<ErrorDto> recordAlreadyExits(
            RecordAlreadyExistsException r,
            WebRequest request
    ){
        ErrorDto errorDto = new ErrorDto(
                r.getMessage(), new Date(), request.getDescription(false));
        return new ResponseEntity<>(errorDto, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(
            Exception e
    ){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
