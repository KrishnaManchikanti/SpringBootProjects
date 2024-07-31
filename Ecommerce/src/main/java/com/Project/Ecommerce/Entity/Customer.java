package com.Project.Ecommerce.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Customer {
    int id;
    String name;
    String email;
    List<Order> orderList;

    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // Methods to manage orders
    public void placeOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null.");
        }
        orderList.add(order);
    }

    public void cancelOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null.");
        }
        if (!orderList.remove(order)) {
            throw new IllegalArgumentException("Order not found in the list.");
        }
    }
}
