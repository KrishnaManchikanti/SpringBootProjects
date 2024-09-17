package com.Project.Ecommerce.Entity;

import java.util.Random;

/**
 * Generates random numbers within a specified range.
 * <p>
 * This class is a helper class used to generate random integers.
 * </p>
 */
public class RandomNumberGenerator {
    public int getRandomValue() {// Minimum 6-digit number
        Random random = new Random();
        int min = 100000;
        int max = 999999;
        return random.nextInt(max - min + 1) + min;
    }
}
