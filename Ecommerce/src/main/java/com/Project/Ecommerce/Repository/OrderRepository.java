package com.Project.Ecommerce.Repository;

import com.Project.Ecommerce.Entity.Order;

import java.util.List;

public interface OrderRepository {
    void addOrder(Order order);
    void removeOrder(Order order);
    Order getOrderById(int id);
    List<Order> getOrdersByCustomerId(int customerId);
}
