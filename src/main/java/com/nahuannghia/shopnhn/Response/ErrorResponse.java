package com.nahuannghia.shopnhn.Response;

import lombok.Getter;
import java.time.Instant;

@Getter
public class ErrorResponse {
    private final int status;
    private final String message;
    private final Instant timestamp;
    private final String errorType;

    public ErrorResponse(int status, String message, String errorType) {
        this.status = status;
        this.message = message;
        this.errorType = errorType;
        this.timestamp = Instant.now();
    }
}