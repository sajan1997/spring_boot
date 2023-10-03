package com.example.springmvc6rest.mapper;

import com.example.springmvc6rest.domain.Customer;
import com.example.springmvc6rest.dto.CustomerDto;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

    Customer customerDtoToCustomer(CustomerDto dto);

    CustomerDto customerToCustomerDto(Customer customer);

}
