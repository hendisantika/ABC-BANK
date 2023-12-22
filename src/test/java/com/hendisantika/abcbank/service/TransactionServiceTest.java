package com.hendisantika.abcbank.service;

import com.hendisantika.abcbank.AbcBankApplication;
import com.hendisantika.abcbank.config.RepositoryTestConfiguration;
import com.hendisantika.abcbank.entity.Account;
import com.hendisantika.abcbank.repository.AccountRepository;
import com.hendisantika.abcbank.repository.TransactionRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * Project : ABC-BANK
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 12/23/23
 * Time: 05:48
 * To change this template use File | Settings | File Templates.
 */
@SpringBootTest(classes = {AbcBankApplication.class,
        RepositoryTestConfiguration.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = {"test"})
public class TransactionServiceTest {

    @Autowired
    private AccountTransactionService txService;

    @Autowired
    private TransactionRepository txRepo;

    @Autowired
    private AccountRepository accountRepo;

    private Account account1 = null;

    private Account account2 = null;

    private Account account3 = null;

    private final EntityManager entityManager = Mockito.mock(EntityManager.class);

    @BeforeEach
    public void setup() {

        account1 = Account.builder().accountNumber("A1").acountHolder("test user").balance(new BigDecimal("89.00"))
                .transactions(new ArrayList<>())
                .build();
        account2 = Account.builder().accountNumber("A2").acountHolder("test user2").balance(new BigDecimal("89.00"))
                .transactions(new ArrayList<>())
                .build();
        account3 = Account.builder().accountNumber("A3").acountHolder("test user3").balance(new BigDecimal("89.00"))
                .transactions(new ArrayList<>())
                .build();
    }
}
