package com.Project.Ecommerce.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    int id;
    int customerId;
    List<Product> productList;
    int totalAmount;
    OrderStatus orderStatus;



    // Methods to manage products and calculate total amount
    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null.");
        }
        productList.add(product);
        calculateTotalAmount();
    }

    public void removeProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null.");
        }
        if (!productList.remove(product)) {
            throw new IllegalArgumentException("Product not found in the list.");
        }
        calculateTotalAmount();
    }

    public void calculateTotalAmount() {
        totalAmount = 0;
        for(Product p:productList){
            totalAmount+= p.getPrice();
        }
    }

}
