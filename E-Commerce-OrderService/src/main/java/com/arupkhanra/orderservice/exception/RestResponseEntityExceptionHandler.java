package com.arupkhanra.orderservice.exception;

import com.arupkhanra.orderservice.external.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse>handelCustomException(CustomException exception){
        return new ResponseEntity<>(new ErrorResponse().builder()
                .errorMassage(exception.getMessage())
                .errorCode(exception.getErrorCode())
                .build(),HttpStatus.valueOf(exception.getStatus()));
    }
}
