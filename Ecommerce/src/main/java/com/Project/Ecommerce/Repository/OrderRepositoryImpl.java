package com.Project.Ecommerce.Repository;

import com.Project.Ecommerce.Entity.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderRepositoryImpl implements OrderRepository{
    //Maintaining listOfOrders for quick access
    List<Order> listOfOrders = new ArrayList<>();
    //created a map where customer is associated with listOfOrders
    private final HashMap<Integer,List<Order>> listOfCustomerOrders = new HashMap<>();

    @Override
    public void addOrder(Order order) {
        int customerId = order.getCustomerId();
        List<Order> orders;
        if(listOfCustomerOrders.containsKey(customerId)){//checking customer is PresentOrNot
            orders = listOfCustomerOrders.get(customerId);
        }else{
            orders = new ArrayList<>();
        }
        orders.add(order);
        listOfOrders.add(order);
        listOfCustomerOrders.put(customerId, orders);
    }

    @Override
    public void removeOrder(Order order) {
        int customerId = order.getCustomerId();
        List<Order> orders;
        if(listOfCustomerOrders.containsKey(customerId)){//checking customer is PresentOrNot
            orders = listOfCustomerOrders.get(customerId);
            orders.remove(order);
            listOfOrders.remove(order);
            listOfCustomerOrders.put(customerId, orders);
        }

    }

    @Override
    public Order getOrderById(int id) {
        for (Order order:listOfOrders) {
            if (order.getId() == id)
                return order;
        }
        return null;
    }

    @Override
    public List<Order> getOrdersByCustomerId(int customerId) {
        if(listOfCustomerOrders.containsKey(customerId)){
            return listOfCustomerOrders.get(customerId);
        }
        return null;
    }
}
