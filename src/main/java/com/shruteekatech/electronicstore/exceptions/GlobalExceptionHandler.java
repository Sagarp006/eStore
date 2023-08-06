package com.shruteekatech.electronicstore.exceptions;

import com.shruteekatech.electronicstore.helper.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.UncheckedIOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
@SuppressWarnings(value = "unused")
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFound(ResourceNotFoundException e) {
        log.error("ResourceNotFoundException occurred: {}", e.getMessage());

        ApiResponse response = ApiResponse.builder()
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .success(false)
                .build();

        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(InvalidFileException.class)
    public ResponseEntity<Map<String, Object>> invalidFile(InvalidFileException e) {
        log.error("InvalidFileException occurred: {}", e.getMessage());

        Map<String, Object> response = Map.of(
                "message", e.getMessage(),
                "status", HttpStatus.BAD_REQUEST
        );

        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<ApiResponse> mediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException e) {
        log.error("HttpMediaTypeNotAcceptableException occurred: {}", e.getMessage());

        ApiResponse response = ApiResponse.builder()
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .success(false)
                .build();

        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(UncheckedIOException.class)
    public ResponseEntity<ApiResponse> unchecked(UncheckedIOException e) {
        log.error("UncheckedIOException occurred: {}", e.getMessage());

        ApiResponse response = ApiResponse.builder()
                .message(e.getMessage())
                .status(HttpStatus.OK)
                .success(false)
                .build();

        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> nonValidArgs(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException occurred: valid arguments is not provided");

        Map<String, String> map = new HashMap<>();

        // Getting all field errors from BindingResult and iterating using a for loop
        e.getBindingResult().getFieldErrors().forEach(fieldError ->
                map.put(fieldError.getField(), fieldError.getDefaultMessage())
        );                 //fields               // error messages

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }
}
