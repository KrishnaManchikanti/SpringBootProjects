package com.Project.Ecommerce.Service;

import com.Project.Ecommerce.Entity.Customer;

public class SMSService implements NotificationService{
    @Override
    public void notify(Customer customer, String message) {
        System.out.println("sms send to "+customer.getName()+": "+message);
    }
}
