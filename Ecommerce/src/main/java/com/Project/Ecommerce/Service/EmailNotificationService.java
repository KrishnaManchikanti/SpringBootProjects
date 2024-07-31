package com.Project.Ecommerce.Service;

import com.Project.Ecommerce.Entity.Customer;

public class EmailNotificationService implements NotificationService{
    @Override
    public void notify(Customer customer, String message) {
        System.out.println("message mailed to "+customer.getName()+" [" +customer.getEmail()+"]: "+message);
    }
}
