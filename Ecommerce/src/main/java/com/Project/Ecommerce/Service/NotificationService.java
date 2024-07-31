package com.Project.Ecommerce.Service;

import com.Project.Ecommerce.Entity.Customer;

public interface NotificationService {
    void notify(Customer customer, String message);
}
