package com.practice_jwt_2.service;

import com.practice_jwt_2.model.Customer;

import java.util.List;

public interface ICustomerService {
    List<Customer> getAllCustomers();

    Customer getCustomerById(Long id);

    void saveCustomer(Customer customer);

    void deleteCustomer(Long id);
}
