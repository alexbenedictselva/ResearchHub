package com.alex.researchhub.common.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public class ResponseBuilder {

    public static <T> ResponseEntity<ApiResponse<T>> success(
            String message,
            T data,
            HttpStatus status) {

        ApiResponse<T> response = ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .status(status.value())
                .timestamp(LocalDateTime.now())
                .data(data)
                .build();

        return ResponseEntity.status(status).body(response);
    }

    public static ResponseEntity<ApiResponse<Void>> success(
            String message,
            HttpStatus status) {

        return success(message, null, status);
    }

}
