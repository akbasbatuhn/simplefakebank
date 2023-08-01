package com.fakedevbankapp.account;

import com.fakedevbankapp.account.dto.converter.AccountDtoConverter;
import com.fakedevbankapp.account.repository.CustomerRepository;
import com.fakedevbankapp.account.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Clock;

public class IntegrationTestSupport extends TestSupport{

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    public Clock clock;

    @Autowired
    public AccountService accountService;

    @Autowired
    public CustomerRepository customerRepository;

    @Autowired
    public AccountDtoConverter converter;

    public final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }
}
