package com.Project.Ecommerce.Entity;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a product in the e-commerce system.
 * <p>
 * This class contains information about the product, such as its price, stock quantity, and status.
 * It also provides methods to manage stock levels.
 * </p>
 */
@Getter
@Setter
public class Product extends NameAndId {

    /**
     * The price of the product.
     */
    private double price;

    /**
     * The current stock quantity of the product.
     */
    private int stockQuantity;

    /**
     * The current status of the product (e.g., AVAILABLE, OUT_OF_STOCK).
     */
    private ProductStatus productStatus;

    private static RandomNumberGenerator randomNumberGenerator = new RandomNumberGenerator();

    /**
     * Constructs a Product with the specified name and initializes default values.
     *
     * @param name the name of the product
     */
    public Product(String name) {
        this.setName(name);
        this.setId(randomNumberGenerator.getRandomValue());
        this.productStatus = ProductStatus.AVAILABLE;
        this.stockQuantity = 5; // default stock quantity
        this.price = 555; // default price
    }

    /**
     * Reduces the stock quantity of the product by the specified amount.
     *
     * @param quantity the quantity to reduce from the stock
     * @return true if the stock was successfully reduced, false if there were not enough stocks
     */
    public boolean reduceStock(int quantity) {
        if (quantity <= stockQuantity) {
            stockQuantity -= quantity;
            if (stockQuantity == 0) {
                this.productStatus = ProductStatus.OUT_OF_STOCK;
            }
            return true;
        }
        return false;
    }

    /**
     * Increases the stock quantity of the product by the specified amount.
     *
     * @param quantity the quantity to add to the stock
     */
    public void increaseStock(int quantity) {
        if (quantity < 0) {
            return;
        }
        stockQuantity += quantity;
        if (productStatus == ProductStatus.OUT_OF_STOCK && stockQuantity > 0) {
            this.productStatus = ProductStatus.AVAILABLE;
        }
    }

    /**
     * Displays the product information.
     * <p>
     * Prints the name and ID of the product to the product.
     * </p>
     */
    @Override
    public void displayInfo() {
        System.out.println("Name: " + getName() + " ID: " + getId());
    }
}
