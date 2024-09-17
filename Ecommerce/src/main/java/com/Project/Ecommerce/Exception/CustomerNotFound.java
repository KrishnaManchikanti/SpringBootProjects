package com.Project.Ecommerce.Exception;

/**
 * Exception thrown when a customer is not found in the system.
 * <p>
 * This exception extends {@link RuntimeException}
 * </p>
 */
public class CustomerNotFound extends RuntimeException {
    public CustomerNotFound(String message) {
        super(message);
    }
}
