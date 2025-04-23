package com.nahuannghia.shopnhn.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrderNotFoundException extends RuntimeException {
    private final String errorCode = "ORDER_NOT_FOUND";
    private final String requestedId;
    
    public OrderNotFoundException(String message, String requestedId) {
        super(message);
        this.requestedId = requestedId;
    }
    
    // Getter methods
}
