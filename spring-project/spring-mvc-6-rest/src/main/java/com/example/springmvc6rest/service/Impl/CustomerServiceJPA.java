package com.example.springmvc6rest.service.Impl;

import com.example.springmvc6rest.dto.CustomerDto;
import com.example.springmvc6rest.mapper.CustomerMapper;
import com.example.springmvc6rest.repository.CustomerRepository;
import com.example.springmvc6rest.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Primary
@RequiredArgsConstructor
public class CustomerServiceJPA implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public List<CustomerDto> getCustomerDetails() {
        return customerRepository.findAll().stream().map(customerMapper::customerToCustomerDto).toList();
    }

    @Override
    public Optional<CustomerDto> getCustomerById(UUID id) {
        return Optional.ofNullable(customerMapper.customerToCustomerDto(customerRepository.findById(id).orElse(null)));
    }

    @Override
    public CustomerDto saveCustomer(CustomerDto customer) {
        return customerMapper.customerToCustomerDto(customerRepository
                .save(customerMapper.customerDtoToCustomer(customer)));
    }

    @Override
    public Optional<CustomerDto> updateCustomer(UUID id, CustomerDto customer) {
        AtomicReference<Optional<CustomerDto>> atomicReference = new AtomicReference<>();

        customerRepository.findById(id).ifPresentOrElse(foundCustomer -> {
            foundCustomer.setCustomerName(customer.getCustomerName());
            atomicReference.set(Optional.of(customerMapper
                    .customerToCustomerDto(customerRepository.save(foundCustomer))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();

    }

    @Override
    public Boolean deleteCustomer(UUID id) {
        if(customerRepository.existsById(id)){
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<CustomerDto> patchCustomerById(UUID id, CustomerDto customer) {
        AtomicReference<Optional<CustomerDto>> atomicReference = new AtomicReference<>();

        customerRepository.findById(id).ifPresentOrElse(foundCustomer -> {
            if (StringUtils.hasText(customer.getCustomerName())){
                foundCustomer.setCustomerName(customer.getCustomerName());
            }
            atomicReference.set(Optional.of(customerMapper
                    .customerToCustomerDto(customerRepository.save(foundCustomer))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }
}
