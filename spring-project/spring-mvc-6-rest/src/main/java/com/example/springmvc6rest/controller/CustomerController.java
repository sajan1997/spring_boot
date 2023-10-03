package com.example.springmvc6rest.controller;

import com.example.springmvc6rest.dto.CustomerDto;
import com.example.springmvc6rest.exception.NotFoundException;
import com.example.springmvc6rest.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/customer")
public class CustomerController {

    private final CustomerService customerService;

    @RequestMapping(method = RequestMethod.GET)
    public List<CustomerDto> getCustomerDetails(){
        return customerService.getCustomerDetails();
    }

    @RequestMapping(method = RequestMethod.GET,value = "{id}")
    public CustomerDto getCustomerById(@PathVariable("id") UUID id){
        return customerService.getCustomerById(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public ResponseEntity<?> saveCustomer(@RequestBody CustomerDto customer){
        CustomerDto savedCustomer = customerService.saveCustomer(customer);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("location","api/customer/"+ savedCustomer.getId());
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable("id") UUID id,@RequestBody CustomerDto customer){
        Optional<CustomerDto> updatedCustomer = customerService.updateCustomer(id,customer);

        if (updatedCustomer.isEmpty()){
            throw new NotFoundException();
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("id") UUID id){

        if (!customerService.deleteCustomer(id)){
            throw new NotFoundException();
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> patchCustomerById(@PathVariable("id") UUID id,@RequestBody CustomerDto customer){
        customerService.patchCustomerById(id,customer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
