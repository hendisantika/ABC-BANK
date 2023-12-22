package com.hendisantika.abcbank.controller;

import com.hendisantika.abcbank.AbcBankApplication;
import com.hendisantika.abcbank.entity.Account;
import com.hendisantika.abcbank.model.TransferToAccountModel;
import com.hendisantika.abcbank.util.AppUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Project : ABC-BANK
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 12/23/23
 * Time: 05:53
 * To change this template use File | Settings | File Templates.
 */
@SpringBootTest(classes = {AbcBankApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class AbcBankControllerTest {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void testTransferOneAccountToAnother() {
        Account a1 = Account.builder().acountHolder("A1").balance(new BigDecimal("29.80")).build();
        Account a2 = Account.builder().acountHolder("A2").balance(new BigDecimal("39.80")).build();

        // create account
        a1 = createAccount(a1);
        a2 = createAccount(a2);

        BigDecimal a1BalBeforeTransfer = a1.getBalance();
        String url = "/transfer";
        // URI (URL) parameters
        Map<String, String> uriParams = new HashMap<>();
        TransferToAccountModel requestObject = TransferToAccountModel.builder().fromAccountNumber(a1.getAccountNumber())
                .toAccountNumber(a2.getAccountNumber()).transferAmount(new BigDecimal("10.80")).build();


        URI uri = UriComponentsBuilder.fromUriString(url).buildAndExpand(uriParams).toUri();

        ResponseEntity<?> transferResponseEntity = this.testRestTemplate.exchange(uri, HttpMethod.POST,
                AppUtil.getEntityWithHttpHeader(requestObject), Object.class);

        Assertions.assertEquals(HttpStatus.OK, transferResponseEntity.getStatusCode());

        // load account1 again
        ResponseEntity<Account> responseEntity = this.testRestTemplate.exchange("/accounts/" + a1.getAccountNumber(),
                HttpMethod.GET,
                AppUtil.getHttpHeader(),
                Account.class);
        // verify
        Assertions.assertEquals(a1BalBeforeTransfer.subtract(new BigDecimal("10.80")),
                responseEntity.getBody().getBalance());

    }
}
