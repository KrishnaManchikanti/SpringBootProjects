package com.Project.Ecommerce.Exception;

/**
 * Exception thrown when a product is out of stock in the system.
 * <p>
 * This exception extends {@link RuntimeException}
 * </p>
 */
public class productOutOfStock extends RuntimeException {
    public productOutOfStock(String message) {
        super(message);
    }
}
