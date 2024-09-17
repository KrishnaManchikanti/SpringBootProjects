package com.Project.Ecommerce.Service;

import com.Project.Ecommerce.Entity.Customer;

/**
 * Interface for sending notifications to customers.
 * <p>
 * This interface defines a contract for notification services that can send messages
 * to customers. Implementations of this interface might send notifications via different
 * channels, such as email or SMS.
 * </p>
 */
public interface NotificationService {
    /**
     * Sends a notification to a customer.
     */
    void notify(Customer customer, String message);
}
