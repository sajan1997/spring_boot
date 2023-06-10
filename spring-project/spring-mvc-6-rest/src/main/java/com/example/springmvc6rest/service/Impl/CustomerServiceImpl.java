package com.example.springmvc6rest.service.Impl;

import com.example.springmvc6rest.domain.Customer;
import com.example.springmvc6rest.service.CustomerService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {

    private Map<UUID, Customer> customerHashMap;

    public CustomerServiceImpl() {

        Customer customer1 = Customer.builder().id(UUID.randomUUID()).customerName("Thor").version(1)
                .createdDate(LocalDateTime.now()).lastModifiedDate(LocalDateTime.now()).build();

        Customer customer2 = Customer.builder().id(UUID.randomUUID()).customerName("IronMan").version(1)
                .createdDate(LocalDateTime.now()).lastModifiedDate(LocalDateTime.now()).build();

        Customer customer3 = Customer.builder().id(UUID.randomUUID()).customerName("SpiderMan").version(1)
                .createdDate(LocalDateTime.now()).lastModifiedDate(LocalDateTime.now()).build();

        customerHashMap = new HashMap<>();
        customerHashMap.put(customer1.getId(),customer1);
        customerHashMap.put(customer2.getId(),customer2);
        customerHashMap.put(customer3.getId(),customer3);

    }

    @Override
    public List<Customer> getCustomerDetails(){
        return new ArrayList<>(customerHashMap.values());
    }

    @Override
    public Customer getCustomerById(UUID id){
        return customerHashMap.getOrDefault(id,null);
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        Customer saveCustomer =  Customer.builder().id(UUID.randomUUID())
                .customerName(customer.getCustomerName()).version(customer.getVersion())
                .createdDate(LocalDateTime.now()).lastModifiedDate(LocalDateTime.now()).build();
        customerHashMap.put(saveCustomer.getId(),saveCustomer);
        return saveCustomer;
    }

}
