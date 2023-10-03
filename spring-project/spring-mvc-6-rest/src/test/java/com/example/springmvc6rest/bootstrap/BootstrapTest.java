package com.example.springmvc6rest.bootstrap;

import com.example.springmvc6rest.repository.BeerRepository;
import com.example.springmvc6rest.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BootstrapTest {

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    CustomerRepository customerRepository;

    Bootstrap bootstrap;

    @BeforeEach
    void setUp() {
        bootstrap = new Bootstrap(beerRepository,customerRepository);
    }

    @Test
    void testRun() throws Exception {
        bootstrap.run(null);

        assertThat(beerRepository.count()).isEqualTo(3);
        assertThat(customerRepository.count()).isEqualTo(3);
    }

}