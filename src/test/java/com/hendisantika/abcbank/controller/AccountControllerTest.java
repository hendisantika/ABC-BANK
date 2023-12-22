package com.hendisantika.abcbank.controller;

import com.hendisantika.abcbank.AbcBankApplication;
import com.hendisantika.abcbank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

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

}
