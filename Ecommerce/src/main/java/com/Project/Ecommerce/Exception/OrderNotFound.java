package com.Project.Ecommerce.Exception;

/**
 * Exception thrown when an order is not found in the system.
 * <p>
 * This exception extends {@link RuntimeException}
 * </p>
 */
public class OrderNotFound extends RuntimeException {
    public OrderNotFound(String message) {
        super(message);
    }
}
