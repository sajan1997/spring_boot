package com.example.springmvc6rest.controller;

import com.example.springmvc6rest.domain.Customer;
import com.example.springmvc6rest.service.CustomerService;
import com.example.springmvc6rest.service.Impl.CustomerServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomerService customerService;

    CustomerServiceImpl customerServiceImpl;

    @BeforeEach
    void setUp() {
        customerServiceImpl = new CustomerServiceImpl();
    }

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testCustomerList() throws Exception {
        given(customerService.getCustomerDetails()).willReturn(customerServiceImpl.getCustomerDetails());

        mockMvc.perform(get("/api/customer")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()",is(3)));
    }

    @Test
    void testCustomerById() throws Exception {
        Customer customer = customerServiceImpl.getCustomerDetails().get(0);

        given(customerService.getCustomerById(customer.getId())).willReturn(customer);

        mockMvc.perform(get("/api/customer/"+customer.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.length()",is(3)));
    }

    @Test
    void saveCustomer() throws Exception {

        Customer customer = customerServiceImpl.getCustomerDetails().get(0);
        customer.setId(null);

        given(customerService.saveCustomer(any(Customer.class)))
                .willReturn(customerServiceImpl.getCustomerDetails().get(1));

        mockMvc.perform(post("/api/customer")
                .accept(MediaType.APPLICATION_JSON).contentType(
                        MediaType.APPLICATION_JSON
                ).content(objectMapper.writeValueAsString(customer))
        ).andExpect(status().isCreated())
                .andExpect(header().exists("location"));

    }
}