package com.shruteekatech.electronicstore.exceptions;


import com.shruteekatech.electronicstore.helper.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFound(ResourceNotFoundException e) {
        return new ResponseEntity<>(ApiResponse.builder().message(e.getMessage())
                .status(HttpStatus.NOT_FOUND).success(false).build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> nonValidArgs(MethodArgumentNotValidException e) {
        Map<String, String> map = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(fieldError ->
                        map.put(fieldError.getField(), //getting fields which have errors
                                fieldError.getDefaultMessage()) //error messages
        );
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }
}
