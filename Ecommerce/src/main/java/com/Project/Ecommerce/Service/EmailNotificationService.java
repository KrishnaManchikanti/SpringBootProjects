package com.Project.Ecommerce.Service;

import com.Project.Ecommerce.Entity.Customer;

/**
 * This class extends{@link NotificationService} and notify over email based on user operations
 */
public class EmailNotificationService implements NotificationService {
    /**
     * @param customer takes customer as input
     * @param message  message to be printed
     */
    @Override
    public void notify(Customer customer, String message) {
        System.out.println("message mailed to " + customer.getName() + " [" + customer.getEmail() + "]: " + message);
    }
}
