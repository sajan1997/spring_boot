package com.example.springmvc6rest.controller;

import com.example.springmvc6rest.dto.CustomerDto;
import com.example.springmvc6rest.service.CustomerService;
import com.example.springmvc6rest.service.Impl.CustomerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CustomerController.class)
class CustomerDtoControllerTest {

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

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<CustomerDto> customerArgumentCaptor;

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
        CustomerDto customer = customerServiceImpl.getCustomerDetails().get(0);

        given(customerService.getCustomerById(customer.getId())).willReturn(Optional.of(customer));

        mockMvc.perform(get("/api/customer/"+customer.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.length()",is(3)));
    }

    @Test
    void testSaveCustomer() throws Exception {

        CustomerDto customer = customerServiceImpl.getCustomerDetails().get(0);
        customer.setId(null);

        given(customerService.saveCustomer(any(CustomerDto.class)))
                .willReturn(customerServiceImpl.getCustomerDetails().get(1));

        mockMvc.perform(post("/api/customer")
                .accept(MediaType.APPLICATION_JSON).contentType(
                        MediaType.APPLICATION_JSON
                ).content(objectMapper.writeValueAsString(customer))
        ).andExpect(status().isCreated())
                .andExpect(header().exists("location"));

    }

    @Test
    void testUpdateCustomer() throws Exception{
        CustomerDto customer = customerServiceImpl.getCustomerDetails().get(0);

        given(customerService.updateCustomer(any(), any())).willReturn(Optional.of(CustomerDto.builder()
                .build()));

        customer.setCustomerName("X-MEN");
        mockMvc.perform(put("/api/customer/"+customer.getId()).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer))).andExpect(status().isNoContent());

        verify(customerService).updateCustomer(any(UUID.class),any(CustomerDto.class));
    }

    @Test
    void testDeleteCustomer() throws Exception{
        CustomerDto customer = customerServiceImpl.getCustomerDetails().get(0);

        given(customerService.deleteCustomer(any())).willReturn(true);

        mockMvc.perform(delete("/api/customer/"+customer.getId()).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        ArgumentCaptor<UUID> argumentCaptor = ArgumentCaptor.forClass(UUID.class);

        verify(customerService).deleteCustomer(argumentCaptor.capture());

        Assertions.assertThat(customer.getId()).isEqualTo(argumentCaptor.getValue());
    }

    @Test
    void testPatchCustomer() throws Exception{
        CustomerDto customer = customerServiceImpl.getCustomerDetails().get(0);

        Map<String, Object> input = new HashMap<>();
        input.put("customerName","TEST");

        mockMvc.perform(patch("/api/customer/"+customer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isNoContent());

        verify(customerService).patchCustomerById(uuidArgumentCaptor.capture(),customerArgumentCaptor.capture());

        Assertions.assertThat(customer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
        Assertions.assertThat(customerArgumentCaptor.getValue().getCustomerName()).isEqualTo(input.get("customerName"));
    }

}