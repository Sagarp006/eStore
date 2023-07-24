package com.shruteekatech.electronicstore.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> resourceNotFound(ResourceNotFoundException e) {
        log.error("ResourceNotFoundException Occurred , Resource is not available in database");
        Map<String, Object> response = Map.of("message", e.getMessage(), "status", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidFileException.class)
    public ResponseEntity<Map<String, Object>> resourceNotFound(InvalidFileException e) {
        Map<String, Object> response = Map.of("message", e.getMessage(), "status", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<String> mediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException e) {
        log.error("The request handler cannot generate a acceptable response ");
        return ResponseEntity.ok(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> nonValidArgs(MethodArgumentNotValidException e) {
        Map<String, String> map = new HashMap<>();
        log.error("ArgumentNotValidException Occurred , provide valid arguments");

        //getting all field errors from bindingResult and iterating using for loop
        e.getBindingResult().getFieldErrors().forEach(fieldError ->
                map.put(fieldError.getField(), //getting fields which have errors
                        fieldError.getDefaultMessage()) //error messages
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }
}
