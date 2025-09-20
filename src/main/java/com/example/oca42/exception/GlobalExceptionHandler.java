package com.example.oca42.exception;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Map<String, Object>> handle(NotFoundException exception, WebRequest request) {
        return ofType(request, HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler({AlreadyExistException.class})
    public ResponseEntity<Map<String, Object>> handle(AlreadyExistException exception, WebRequest request) {
        return ofType(request, HttpStatus.CONFLICT, exception.getMessage());
    }

    @ExceptionHandler({ExpiredJwtException.class})
    public ResponseEntity<Map<String, Object>> handleRuntimeException(ExpiredJwtException exception, WebRequest request) {
        return ofType(request, HttpStatus.UNAUTHORIZED, exception.getMessage());
    }

    private ResponseEntity<Map<String, Object>> ofType(WebRequest request, HttpStatus status, String message) {
        return ofType(request, status, message, null);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        List<ConstraintsViolationError> validationErrors = new ArrayList<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                validationErrors.add(new ConstraintsViolationError(error.getField(), error.getDefaultMessage()))
        );

        return ofType(request, HttpStatus.BAD_REQUEST, "Validation errors", validationErrors);
    }


    private ResponseEntity<Map<String, Object>> ofType(WebRequest request, HttpStatus status, String message, List<ConstraintsViolationError> validationErrors) {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("STATUS", status.value());
        attributes.put("ERROR", status.getReasonPhrase());
        attributes.put("MESSAGE", message);
        attributes.put("ERRORS", validationErrors);
        attributes.put("PATH", ((ServletWebRequest) request).getRequest().getRequestURI());
        return new ResponseEntity<>(attributes, status);
    }

}