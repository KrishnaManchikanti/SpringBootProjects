package com.Project.Ecommerce;


import com.Project.Ecommerce.Entity.*;
import com.Project.Ecommerce.Service.EmailNotificationService;
import com.Project.Ecommerce.Service.NotificationService;
import com.Project.Ecommerce.Service.SMSService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        Product product1 = new Product(1, "Laptop", 1000, 10);
        Product product2 = new Product(2, "Mouse", 50, 100);

        Customer customer = new Customer(1, "John Doe", "john.doe@example.com", new ArrayList<Order>());

        Order order = new Order(1, customer.getId(),new ArrayList<Product>(), 200, OrderStatus.PENDING);
        order.addProduct(product1);
        order.addProduct(product2);
//Customer placing an order
        customer.placeOrder(order);
//Updating product stock.
        product1.reduceStock(1);
        product2.increaseStock(2);
//Calculating the total amount of the order.
        System.out.println("Order Total Amount: " + order.getTotalAmount());

        System.out.println("Product1 Stock: " + product1.getStockQuantity());
        System.out.println("Product2 Stock: " + product2.getStockQuantity());

//Notifying the customer.
        NotificationService emailService = new EmailNotificationService();
        emailService.notify(customer, "Your order has been placed.");

        NotificationService smsService = new SMSService();
        smsService.notify(customer, "Your order has been placed.");
    }
}
