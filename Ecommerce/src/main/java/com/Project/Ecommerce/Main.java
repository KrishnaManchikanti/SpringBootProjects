package com.Project.Ecommerce;

import com.Project.Ecommerce.Entity.*;
import com.Project.Ecommerce.Service.EmailNotificationService;
import com.Project.Ecommerce.Service.NotificationService;
import com.Project.Ecommerce.Service.SMSService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.println("welcome to Shopping Cart");
        Scanner s = new Scanner(System.in);

        System.out.println("Please enter your name & email");
        String customerName = s.next();
        String customerEmail = s.next();
        Customer customer1 = new Customer(customerName, customerEmail);

        Order order = new Order(customer1.getId());
        System.out.println(customer1.getName() + " :Do you like to order \n Select 1 to order, Select 2 exist");
        int start = s.nextInt();
        boolean shopping = true;
        if (start == 1) {
            while (shopping) {
                System.out.println("please enter product name");
                String productName = s.next();
                System.out.println("please enter quantity to place order");
                int numProducts = s.nextInt();
                order.addProduct(new Product(productName), numProducts);
                System.out.println("Do you want to add another product \n Select 1 if yes, Select 2 done");

                start = s.nextInt();
                if (start == 2)
                    shopping = false;
            }
            System.out.println("totalAmount: " + Order.totalAmount);
            customer1.placeOrder(order);

            NotificationService emailService = new EmailNotificationService();
            emailService.notify(customer1, "Your order has been placed.");
            NotificationService smsService = new SMSService();
            smsService.notify(customer1, "Your order has been placed.");

            customer1.getAllOrders();
            customer1.displayInfo();
           Customer.getOrdersByCustomerId(customer1.getId());
           System.out.println(customer1.getOrderById(order.getId()));

            //modify & cancel order
            order.removeProduct(order.getProductList().get(0).getProduct(), 1);
            customer1.cancelOrder(order);

            customer1.getAllOrders();
            customer1.displayInfo();
        }
    }
}





