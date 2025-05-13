
package com.nahuannghia.shopnhn.exeption;
public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException(String message) {
        super(message);
    }
}
