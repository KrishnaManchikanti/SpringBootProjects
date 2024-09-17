package com.Project.Ecommerce.Repository;

import com.Project.Ecommerce.Entity.Order;

import java.util.List;

/**
 * Interface for managing orders in the e-commerce system.
 * <p>
 * This interface defines methods for adding, removing, retrieving, and listing orders.
 * Implementations of this interface are responsible for persisting orders and providing access.
 * </p>
 */
public interface OrderRepository {
    void addOrder(Order order);

    void removeOrder(Order order);

    Order getOrderById(int id);

    List<Order> getOrdersByCustomerId(int customerId);

    List<Order> getAllOrders();
}
