package com.example.springmvc6rest.controller;

import com.example.springmvc6rest.domain.Customer;
import com.example.springmvc6rest.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("api/customer")
public class CustomerController {

    private final CustomerService customerService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Customer> getCustomerDetails(){
        return customerService.getCustomerDetails();
    }

    @RequestMapping(method = RequestMethod.GET,value = "{id}")
    private Customer getCustomerById(@PathVariable("id") UUID id){
        return customerService.getCustomerById(id);
    }

    @PostMapping
    private ResponseEntity saveCustomer(@RequestBody Customer customer){
        Customer savedCustomer = customerService.saveCustomer(customer);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("location","api/customer/"+ savedCustomer.getId());
        return new ResponseEntity(httpHeaders, HttpStatus.CREATED);
    }
}
