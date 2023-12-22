package com.hendisantika.abcbank.controller;

import com.hendisantika.abcbank.AbcBankApplication;
import com.hendisantika.abcbank.entity.Account;
import com.hendisantika.abcbank.service.AccountService;
import com.hendisantika.abcbank.util.AppUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * Project : ABC-BANK
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 12/23/23
 * Time: 05:51
 * To change this template use File | Settings | File Templates.
 */
@SpringBootTest(classes = {AbcBankApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = {"test"})
public class AccountControllerTest {

    @Autowired
    protected WebApplicationContext wac;

    @LocalServerPort
    int randomServerPort;
    MockMvc mockMvc;

    @Autowired
    private AccountController accountController;

    @MockBean
    private AccountService accountService;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private Account account = null;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.accountController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()).build();// Standalone context

        account = Account.builder().acountHolder("test user").balance(new BigDecimal("89.00")).build();
    }

    @Test
    public void createAccountTest() throws Exception {
        Mockito.when(accountService.saveUpdate(Mockito.any())).thenReturn(account);
        Mockito.when(accountService.findByAccountNumber(Mockito.any())).thenReturn(account);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/accounts")
                .accept(MediaType.APPLICATION_JSON, MediaType.ALL);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        ResponseEntity<Account> responseEntity = this.testRestTemplate.exchange(
                "/accounts/" + account.getAccountNumber(),
                HttpMethod.GET, AppUtil.getHttpHeader(),
                Account.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(account, responseEntity.getBody());
    }
}
