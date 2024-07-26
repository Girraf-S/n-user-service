package com.solbeg.nuserservice.controller;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.solbeg.nuserservice.exception.AppException;
import com.solbeg.nuserservice.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestResponseExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorResponse> handleConflict(
            AppException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        System.out.println(ex.getMessage());
        System.out.println(ex.getMessage());
        System.out.println(ex.getMessage());
        System.out.println(ex.getMessage());
        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleConflict(
            IllegalArgumentException ex) {
        System.out.println(ex.getMessage());
        System.out.println(ex.getMessage());
        System.out.println(ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ErrorResponse> handleConflict(
            TokenExpiredException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        System.out.println(ex.getMessage());
        System.out.println(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleConflict(
            RuntimeException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
