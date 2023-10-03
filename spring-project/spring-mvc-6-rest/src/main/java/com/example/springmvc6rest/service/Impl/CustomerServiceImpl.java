package com.example.springmvc6rest.service.Impl;

import com.example.springmvc6rest.dto.CustomerDto;
import com.example.springmvc6rest.service.CustomerService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {

    private Map<UUID, CustomerDto> customerHashMap;

    public CustomerServiceImpl() {

        CustomerDto customer1 = CustomerDto.builder().id(UUID.randomUUID()).customerName("Thor").version(1)
                .createdDate(LocalDateTime.now()).lastModifiedDate(LocalDateTime.now()).build();

        CustomerDto customer2 = CustomerDto.builder().id(UUID.randomUUID()).customerName("IronMan").version(1)
                .createdDate(LocalDateTime.now()).lastModifiedDate(LocalDateTime.now()).build();

        CustomerDto customer3 = CustomerDto.builder().id(UUID.randomUUID()).customerName("SpiderMan").version(1)
                .createdDate(LocalDateTime.now()).lastModifiedDate(LocalDateTime.now()).build();

        customerHashMap = new HashMap<>();
        customerHashMap.put(customer1.getId(),customer1);
        customerHashMap.put(customer2.getId(),customer2);
        customerHashMap.put(customer3.getId(),customer3);

    }

    @Override
    public List<CustomerDto> getCustomerDetails(){
        return new ArrayList<>(customerHashMap.values());
    }

    @Override
    public Optional<CustomerDto> getCustomerById(UUID id){
        return Optional.ofNullable(customerHashMap.getOrDefault(id, null));
    }

    @Override
    public CustomerDto saveCustomer(CustomerDto customer) {
        CustomerDto saveCustomer =  CustomerDto.builder().id(UUID.randomUUID())
                .customerName(customer.getCustomerName()).version(customer.getVersion())
                .createdDate(LocalDateTime.now()).lastModifiedDate(LocalDateTime.now()).build();
        customerHashMap.put(saveCustomer.getId(),saveCustomer);
        return saveCustomer;
    }

    @Override
    public Optional<CustomerDto> updateCustomer(UUID id, CustomerDto customer) {
        CustomerDto existCust = customerHashMap.get(id);
        existCust.setCustomerName(customer.getCustomerName());
        existCust.setVersion(customer.getVersion());
        customerHashMap.put(id,existCust);
        return Optional.of(existCust);
    }

    @Override
    public Boolean deleteCustomer(UUID id) {
        customerHashMap.remove(id);
        return true;
    }

    public Optional<CustomerDto> patchCustomerById(UUID id, CustomerDto customer){
        CustomerDto existing = customerHashMap.get(id);

        if(StringUtils.hasText(customer.getCustomerName())){
            existing.setCustomerName(customer.getCustomerName());
        }
        if(customer.getVersion() != null){
            existing.setVersion(customer.getVersion());
        }
        existing.setLastModifiedDate(LocalDateTime.now());
        return Optional.of(existing);
    }

}
