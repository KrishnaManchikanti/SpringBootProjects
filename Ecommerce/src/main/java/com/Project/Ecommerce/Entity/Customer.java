package com.Project.Ecommerce.Entity;

import com.Project.Ecommerce.Repository.OrderRepository;
import com.Project.Ecommerce.Repository.OrderRepositoryImpl;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a customer in the e-commerce system.
 * <p>
 * This class is responsible for managing customer-related operations such as placing and canceling orders.
 * </p>
 *
 * @implNote This class uses the `NameAndId` superclass to inherit common properties for customer identification.
 */
@Getter
@Setter
public class Customer extends NameAndId {

    private String email;
    private List<Order> orderList = new ArrayList<>();

    private final OrderRepository orderRepository = new OrderRepositoryImpl();
    private final RandomNumberGenerator randomNumberGenerator = new RandomNumberGenerator();

    public Customer(String name, String email) {
        this.setName(name);
        this.email = email;
        this.setId(randomNumberGenerator.getRandomValue());
    }

    /**
     * @param order as input then order will be placed and update the order status
     */
    public void placeOrder(Order order) {
        if (order == null || order.getProductList().isEmpty() || order.getCustomerId() == 0) {
            throw new IllegalArgumentException("Order cannot be null.");
        }

        order.setOrderStatus(OrderStatus.SHIPPED);
        orderRepository.addOrder(order);
        order.setOrderStatus(OrderStatus.DELIVERED);

    }

    /**
     * @param order as input then cancel the order and update the order status
     */
    public void cancelOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null.");
        }
        order.setOrderStatus(OrderStatus.CANCELLED);
        orderRepository.removeOrder(order);
        System.out.println("order canceled successfully");
    }

    /**
     * @param id check id is present in order
     * @return an order if found
     */
    public Order getOrderById(int id) {
        return orderRepository.getOrderById(id);
    }

    /**
     * @param customerId check id is present in order & Print the order
     */
    public void getOrdersByCustomerId(int customerId) {
        List<Order> orders = orderRepository.getOrdersByCustomerId(customerId);
        printOrder(orders);
    }

    /**
     * printOrder is helperMethod takes @param orders as input
     */
    private static void printOrder(List<Order> orders) {
        if (orders.isEmpty()) {
            System.out.println("Your orderList is Empty: Start Shopping:)");
            return;
        }
        for (Order order : orders) {
            System.out.print("CustomerId: " + order.getCustomerId() + "getOrderStatus: " + order.getOrderStatus());
            System.out.println();
            List<ItemQuantity> productList = order.getProductList();
            for (ItemQuantity item : productList) {
                System.out.println("ordered product " + item.getProduct().getName() + " is " + item.getProduct().getProductStatus() + " with price " + item.getProduct().getPrice());
            }
            System.out.println();
        }
    }

    /**
     * will print listOfOrders
     */
    public void getAllOrders() {
        List<Order> orders = orderRepository.getAllOrders();
        printOrder(orders);
    }

    /**
     * print customerDetails
     */
    @Override
    public void displayInfo() {
        System.out.println("name: " + getName() + " id: " + getId());
    }
}
