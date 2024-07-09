package com.solbeg.nuserservice.controller;

import com.solbeg.nuserservice.exception.HeaderException;
import com.solbeg.nuserservice.model.Response;
import org.aspectj.lang.annotation.AfterThrowing;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestResponseExceptionHandler {

    @ExceptionHandler(HeaderException.class)
    @AfterThrowing(pointcut = "execution(* com.solbeg.nuserservice.controller.*.*(..))", throwing = "HeaderException")
    public ResponseEntity<Response> handleConflict(
            HeaderException ex) {
        Response response = new Response(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @AfterThrowing(pointcut = "execution(* com.solbeg.nuserservice.controller.*.*(..))", throwing = "IllegalArgumentException")
    public ResponseEntity<Response> handleConflict(
            IllegalArgumentException ex) {
        Response response = new Response(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
