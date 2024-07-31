package com.Project.Ecommerce.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private int id;
    private String name;
    private int price;
    private int stockQuantity;

    public void reduceStock(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity to reduce cannot be negative.");
        }
        if (quantity > stockQuantity) {
            throw new IllegalArgumentException("Not enough stock to reduce.");
        }
        this.stockQuantity -= quantity;
    }

    public void increaseStock(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity to increase cannot be negative.");
        }
        this.stockQuantity += quantity;
    }
}
