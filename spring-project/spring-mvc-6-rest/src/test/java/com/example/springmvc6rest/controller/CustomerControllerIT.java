package com.example.springmvc6rest.controller;

import com.example.springmvc6rest.domain.Customer;
import com.example.springmvc6rest.dto.CustomerDto;
import com.example.springmvc6rest.exception.NotFoundException;
import com.example.springmvc6rest.mapper.CustomerMapper;
import com.example.springmvc6rest.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CustomerControllerIT {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerController customerController;

  @Autowired
    CustomerMapper customerMapper;

    @Rollback
    @Transactional
    @Test
    void testListAllEmptyList() {
        customerRepository.deleteAll();
        List<CustomerDto> dtos = customerController.getCustomerDetails();

        assertThat(dtos.size()).isEqualTo(0);
    }

    @Test
    void getCustomerDetails() {
        List<CustomerDto> dtos = customerController.getCustomerDetails();

        assertThat(dtos.size()).isEqualTo(3);
    }

    @Test
    void testGetByIdNotFound() {
        assertThrows(NotFoundException.class,()->{
            customerController.getCustomerById(UUID.randomUUID());
        });
    }

    @Test
    void getCustomerById() {
        Customer customer = customerRepository.findAll().get(0);
        CustomerDto customerDTO = customerController.getCustomerById(customer.getId());
        assertThat(customerDTO).isNotNull();
    }

    @Rollback
    @Transactional
    @Test
    void saveCustomer() {
        CustomerDto customerDto = CustomerDto.builder()
                .customerName("TEST")
                .build();

        ResponseEntity responseEntity = customerController.saveCustomer(customerDto);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[2]);

        Customer customer = customerRepository.findById(savedUUID).get();
        assertThat(customer).isNotNull();
    }

    @Rollback
    @Transactional
    @Test
    void updateCustomer() {
        Customer customer = customerRepository.findAll().get(0);
        CustomerDto customerDto = customerMapper.customerToCustomerDto(customer);
        customerDto.setId(null);
        customerDto.setVersion(null);
        final String customerName = "TEST";
        customerDto.setCustomerName(customerName);

        ResponseEntity responseEntity = customerController.updateCustomer(customer.getId(), customerDto);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Customer updatedCustomer = customerRepository.findById(customer.getId()).get();
        assertThat(updatedCustomer.getCustomerName()).isEqualTo(customerName);
    }

    @Test
    void testDeleteNotFound() {
        assertThrows(NotFoundException.class, () -> {
            customerController.deleteCustomer(UUID.randomUUID());
        });
    }

    @Rollback
    @Transactional
    @Test
    void deleteCustomer() {
        Customer customer = customerRepository.findAll().get(0);

        ResponseEntity responseEntity = customerController.deleteCustomer(customer.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        assertThat(customerRepository.findById(customer.getId()).isEmpty());
    }

    @Rollback
    @Transactional
    @Test
    void patchCustomerById() {
        Customer customer = customerRepository.findAll().get(0);
        CustomerDto customerDto = customerMapper.customerToCustomerDto(customer);
        customerDto.setId(null);
        customerDto.setVersion(null);
        final String customerName = "TEST";
        customerDto.setCustomerName(customerName);

        ResponseEntity responseEntity = customerController.patchCustomerById(customer.getId(), customerDto);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Customer updatedCustomer = customerRepository.findById(customer.getId()).get();
        assertThat(updatedCustomer.getCustomerName()).isEqualTo(customerName);
    }
}