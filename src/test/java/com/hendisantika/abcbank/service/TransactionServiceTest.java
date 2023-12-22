package com.hendisantika.abcbank.service;

import com.hendisantika.abcbank.AbcBankApplication;
import com.hendisantika.abcbank.config.RepositoryTestConfiguration;
import com.hendisantika.abcbank.entity.Account;
import com.hendisantika.abcbank.repository.AccountRepository;
import com.hendisantika.abcbank.repository.TransactionRepository;
import jakarta.persistence.EntityManager;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

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

    private final Account account1 = null;

    private final Account account2 = null;

    private final Account account3 = null;

    private final EntityManager entityManager = Mockito.mock(EntityManager.class);
}
