package com.example.springmvc6rest.service;

import com.example.springmvc6rest.domain.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    List<Customer> getCustomerDetails();

    Customer getCustomerById(UUID id);

    Customer saveCustomer(Customer customer);

    Customer updateCustomer(UUID id,Customer customer);

    void deleteCustomer(UUID id);

    void patchCustomerById(UUID id, Customer customer);
}
