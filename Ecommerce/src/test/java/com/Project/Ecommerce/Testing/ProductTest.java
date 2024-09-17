package com.Project.Ecommerce.Testing;

import com.Project.Ecommerce.Entity.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductTest {
    @Test
    public void testReduceStock() {
        Product product = new Product("laptop");
        product.reduceStock(3);
        assertEquals(7, product.getStockQuantity());
    }

    @Test
    public void testIncreaseStock() {
        Product product = new Product("mobile");
        product.increaseStock(5);
        assertEquals(10, product.getStockQuantity());
    }
}
