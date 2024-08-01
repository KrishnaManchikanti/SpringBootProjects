package com.Project.Ecommerce.Entity;

/**
 * Represents the different status of an order in the e-commerce system.
 * <p>
 * This enum defines the possible states of an order from when it is placed until it is delivered or cancelled.
 * </p>
 */
public enum OrderStatus {
    PENDING,
    SHIPPED,
    DELIVERED,
    CANCELLED
}
