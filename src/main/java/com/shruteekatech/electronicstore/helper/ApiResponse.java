package com.shruteekatech.electronicstore.helper;
import lombok.*;
import org.springframework.http.HttpStatus;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class ApiResponse {
    private String message;
    private HttpStatus status;
    private Boolean success;

}
