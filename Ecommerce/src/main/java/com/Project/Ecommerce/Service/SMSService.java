package com.Project.Ecommerce.Service;

import com.Project.Ecommerce.Entity.Customer;

/**
 * This class extends{@link NotificationService} and notify over email based on user operations
 */
public class SMSService implements NotificationService {
    /**
     * @param customer takes customer as input
     * @param message  message to be printed
     */
    @Override
    public void notify(Customer customer, String message) {
        System.out.println("sms send to " + customer.getName() + ": " + message);
    }
}
