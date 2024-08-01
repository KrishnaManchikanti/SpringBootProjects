package com.Project.Ecommerce.Entity;

import com.Project.Ecommerce.Exception.ProductNotFound;
import com.Project.Ecommerce.Exception.productOutOfStock;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an order placed by a customer.
 * <p>
 * This class manages the list of products in the order, including adding and removing products,
 * and calculating the total amount for the order.
 * </p>
 */
@Data
public class Order {

    private int id;
    private int customerId;
    private List<ItemQuantity> productList = new ArrayList<>();
    public static double totalAmount;
    private OrderStatus orderStatus;

    private static RandomNumberGenerator randomNumberGenerator = new RandomNumberGenerator();

    /**
     * Constructs an Order for a specific customer.
     *
     * @param customerId the ID of the customer placing the order
     */
    public Order(int customerId) {
        this.id = randomNumberGenerator.getRandomValue();
        this.orderStatus = OrderStatus.PENDING;
        this.customerId = customerId;
    }

    /**
     * Checks if the given product is already present in the order.
     *
     * @param product the product to check
     * @return the ItemQuantity object if the product is found, null otherwise
     */
    private ItemQuantity isPresent(Product product) {
        for (ItemQuantity li : productList) {
            if (li.getProduct().equals(product)) {
                return li;
            }
        }
        return null;
    }

    /**
     * Adds a product to the order with the specified quantity.
     *
     * @param product     the product to add
     * @param numProducts the number of units the product to add
     * @throws IllegalArgumentException if the product is null or quantity is negative
     * @throws productOutOfStock        if the product is out of stock
     */
    public void addProduct(Product product, int numProducts) {
        if (product == null || product.getName() == null || numProducts < 0) {
            throw new IllegalArgumentException("Product cannot be null or quantity cannot be negative.");
        }

        if (product.reduceStock(numProducts)) {
            ItemQuantity itemQuantity = isPresent(product);
            if (itemQuantity != null) {
                itemQuantity.setNumProducts(itemQuantity.getNumProducts() + numProducts);
            } else {
                productList.add(new ItemQuantity(product, numProducts));
            }
            calculateTotalAmount(product.getPrice() * numProducts);
        } else {
            throw new productOutOfStock("Product is not available.");
        }
    }

    /**
     * Removes a specified quantity of a product from the order.
     *
     * @param product     the product to remove
     * @param numProducts the number of units the product to remove
     * @throws IllegalArgumentException if the product is null or quantity is negative
     * @throws ProductNotFound          if the product is not found in the order
     */
    public void removeProduct(Product product, int numProducts) {
        if (product == null || numProducts < 0) {
            throw new IllegalArgumentException("Product cannot be null or quantity cannot be negative.");
        }

        ItemQuantity itemQuantity = isPresent(product);
        if (itemQuantity == null) {
            throw new ProductNotFound("Product not found in the order with id: " + product.getId());
        }

        if (itemQuantity.getNumProducts() - numProducts == 0) {
            productList.remove(itemQuantity);
        } else {
            itemQuantity.setNumProducts(itemQuantity.getNumProducts() - numProducts);
        }

        product.increaseStock(numProducts);
        calculateTotalAmount(-(product.getPrice() * numProducts));
        System.out.println("Product removed with the given quantity.");
    }

    /**
     * Calculates and updates the total amount of the order.
     *
     * @param price the amount to add or subtract from the total amount
     */
    private void calculateTotalAmount(double price) {
        totalAmount += price;
        System.out.println("Total amount: " + totalAmount);
    }
}
