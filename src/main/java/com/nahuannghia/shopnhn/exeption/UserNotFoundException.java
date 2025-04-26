package com.nahuannghia.shopnhn.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a user is not found in the system.
 * Automatically returns HTTP 404 (NOT_FOUND) status when thrown from a controller.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new UserNotFoundException with default message.
     */
    public UserNotFoundException() {
        super("User not found");
    }

    /**
     * Constructs a new UserNotFoundException with custom message.
     * @param message the detail message
     */
    public UserNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new UserNotFoundException with custom message and cause.
     * @param message the detail message
     * @param cause the root cause
     */
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new UserNotFoundException with user ID.
     * @param userId the ID of the user that was not found
     */
    public UserNotFoundException(Long userId) {
        super("User with ID " + userId + " not found");
    }

    /**
     * Constructs a new UserNotFoundException with username/email.
     * @param usernameOrEmail the username or email that was not found
     * @param isEmail flag indicating if the parameter is an email
     */
    public UserNotFoundException(String usernameOrEmail, boolean isEmail) {
        super((isEmail ? "User with email " : "User with username ") + usernameOrEmail + " not found");
    }
}