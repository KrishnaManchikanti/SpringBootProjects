package com.Project.Ecommerce.Entity;

import lombok.Data;


/**
 * This class is helper class where customer & product extends the common code
 */
@Data
public abstract class NameAndId {
    private int id;
    private String name;

    public abstract void displayInfo();
}
