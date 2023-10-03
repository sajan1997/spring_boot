package com.example.springmvc6rest.service;

import com.example.springmvc6rest.dto.CustomerDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    List<CustomerDto> getCustomerDetails();

    Optional<CustomerDto> getCustomerById(UUID id);

    CustomerDto saveCustomer(CustomerDto customer);

    Optional<CustomerDto> updateCustomer(UUID id, CustomerDto customer);

    Boolean deleteCustomer(UUID id);

    Optional<CustomerDto> patchCustomerById(UUID id, CustomerDto customer);
}
