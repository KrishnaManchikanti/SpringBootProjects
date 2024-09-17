package com.Project.Ecommerce.Exception;

/**
 * Exception thrown when a product is not found in the system.
 * <p>
 * This exception extends {@link RuntimeException}
 * </p>
 */
public class ProductNotFound extends RuntimeException {
    public ProductNotFound(String message) {
        super(message);
    }
}
