package com.Project.Ecommerce.Repository;

import com.Project.Ecommerce.Entity.Order;
import com.Project.Ecommerce.Exception.CustomerNotFound;
import com.Project.Ecommerce.Exception.OrderNotFound;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the {@link OrderRepository} interface.
 * <p>
 * This class provides an in-memory storage solution for orders.
 * <ul>
 *     <li>A list of all orders.</li>
 *     <li>A map associating customer IDs with their respective orders.</li>
 *     <li>A map for efficient retrieval of orders by their ID.</li>
 * </ul>
 * </p>
 */
@Getter
@Setter
public class OrderRepositoryImpl implements OrderRepository {
    /**
     * A list to store all orders.
     */
    private final List<Order> listOfOrders = new ArrayList<>();

    /**
     * A map where each customer ID is associated with a list of their orders.
     */
    private final Map<Integer, List<Order>> listOfCustomerOrders = new HashMap<>();

    /**
     * A map to efficiently retrieve orders by their ID.
     */
    private final Map<Integer, Order> orderById = new HashMap<>();

    /**
     * Adds a new order to the repository.
     * <p>
     * If the customer ID in the order is zero, it throws a {@link CustomerNotFound} exception.
     * If the customer is not already in the repository, a new entry is created for them.
     * The order is added to both the list of all orders and the list of orders for the customer.
     * </p>
     *
     * @param order the order to be added
     * @throws IllegalArgumentException if the order is null
     * @throws CustomerNotFound         if the customer ID is zero
     */
    @Override
    public void addOrder(Order order) {
        if (order == null || order.getProductList().isEmpty()) {
            throw new IllegalArgumentException("Order cannot be null");
        }

        int customerId = order.getCustomerId();
        if (customerId == 0) {
            throw new CustomerNotFound("Please register as a Customer ");
        }

        List<Order> orders;
        if (listOfCustomerOrders.containsKey(customerId)) {//checking customer is PresentOrNot
            orders = listOfCustomerOrders.get(customerId);
        } else {
            orders = new ArrayList<>();
            listOfCustomerOrders.put(customerId, orders);
        }
        orders.add(order);
        listOfOrders.add(order);
        orderById.put(order.getId(), order);
    }

    /**
     * Removes an existing order from the repository.
     * <p>
     * If the customer ID in the order is zero or the customer is not found, a {@link CustomerNotFound} exception is thrown.
     * If the order cannot be found in the list of orders for the customer, an {@link OrderNotFound} exception is thrown.
     * The order is removed from all relevant lists and maps.
     * </p>
     *
     * @param order the order to be removed
     * @throws IllegalArgumentException if the order is null
     * @throws CustomerNotFound         if the customer ID is zero or the customer is not found
     * @throws OrderNotFound            if the order is not found for deletion
     */
    @Override
    public void removeOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }

        int customerId = order.getCustomerId();
        if (customerId == 0) {
            throw new CustomerNotFound("Please register as a Customer ");
        }

        if (!listOfCustomerOrders.containsKey(customerId)) {//checking customer is PresentOrNot
            throw new CustomerNotFound("Please check your id: " + customerId);
        }

        List<Order> orders = listOfCustomerOrders.get(customerId);
        if (!orders.remove(order)) {
            throw new OrderNotFound("Deletion failed");
        }

        listOfOrders.remove(order);
        listOfCustomerOrders.remove(customerId, orders);
        orderById.remove(order.getId());
    }

    @Override
    public Order getOrderById(int id) {
        Order order = orderById.get(id);
        if (ObjectUtils.isEmpty(order))
            throw new OrderNotFound("Order Not Found with id: " + id);
        return order;
    }

    /**
     * Retrieves an order from the repository by its ID.
     *
     * @throws OrderNotFound if no order is found with the given ID
     */
    @Override
    public List<Order> getOrdersByCustomerId(int customerId) {
        if (listOfCustomerOrders.containsKey(customerId)) {
            return listOfCustomerOrders.get(customerId);
        }
        throw new OrderNotFound("Orders Not Found with customerId: " + customerId);
    }

    /**
     * Retrieves a list of orders associated with a specific customer.
     *
     * @throws OrderNotFound if no orders are found for the given customer ID
     */
    @Override
    public List<Order> getAllOrders() {
        return listOfOrders;
    }

}
