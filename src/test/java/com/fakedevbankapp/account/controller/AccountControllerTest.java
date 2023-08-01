package com.fakedevbankapp.account.controller;

import com.fakedevbankapp.account.IntegrationTestSupport;
import com.fakedevbankapp.account.dto.CreateAccountRequest;
import com.fakedevbankapp.account.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
        "server-port=0",
        "command.line.runner.enabled=false"
})
@DirtiesContext
class AccountControllerTest extends IntegrationTestSupport{

    @Test
    public void testCreateAccount_whenCustomerIdExists_shouldCreateAccountAndReturnAccountDto() throws Exception{
        Customer customer = customerRepository.save(generateCustomer());
        CreateAccountRequest request = generateCreateAccountRequest(customer.getId(), 100);

        System.out.println(customer);
        System.out.println(request);

        this.mockMvc.perform(post(ACCOUNT_API_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writer().withDefaultPrettyPrinter().writeValueAsString(request)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.balance", is(100)))
                .andExpect(jsonPath("$.customer.id", is(customer.getId())))
                .andExpect(jsonPath("$.customer.name", is(customer.getName())))
                .andExpect(jsonPath("$.customer.surname", is(customer.getSurname())))
                .andExpect(jsonPath("$.transactions", hasSize(1)));

    }

    @Test
    public void testCreateAccount_whenCustomerIdDoesNotExist_shouldReturn404NotFound() throws Exception {
        CreateAccountRequest request = generateCreateAccountRequest(100);

        this.mockMvc.perform(post(ACCOUNT_API_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writer().withDefaultPrettyPrinter().writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateAccount_whenRequestHasNoCustomerId_shouldReturn400BadRequest() throws Exception {
        CreateAccountRequest request = generateCreateAccountRequest("", 100);

        this.mockMvc.perform(post(ACCOUNT_API_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writer().withDefaultPrettyPrinter().writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testCreateAccount_whenRequestHasLessThanZeroInitialCredit_shouldReturn400BadRequest() throws Exception {
        CreateAccountRequest request = generateCreateAccountRequest("id", -100);

        this.mockMvc.perform(post(ACCOUNT_API_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writer().withDefaultPrettyPrinter().writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

}