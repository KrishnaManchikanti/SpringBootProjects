package com.Project.Ecommerce.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * This class a helper class where an item contains the product and its associated quantities
 */
@Data
@AllArgsConstructor
public class ItemQuantity {
    private Product product;
    private int numProducts;
}
