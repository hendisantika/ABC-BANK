package com.hendisantika.abcbank.service;

import com.hendisantika.abcbank.AbcBankApplication;
import com.hendisantika.abcbank.config.RepositoryTestConfiguration;
import com.hendisantika.abcbank.entity.Account;
import com.hendisantika.abcbank.entity.Transaction;
import com.hendisantika.abcbank.repository.AccountRepository;
import com.hendisantika.abcbank.repository.TransactionRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.validateMockitoUsage;
import static org.mockito.Mockito.when;

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

    @AfterEach
    public void validate() {
        validateMockitoUsage();
    }

    @Test
    public void getTransactionHistoryTest() {
        List<Transaction> list = new ArrayList<Transaction>();
        Transaction a1 = Transaction.builder()
                .account(account1)
                .amount(new BigDecimal("87.90")).discriminator(Transaction.TransactionType.DEBIT).build();

        Transaction a2 = Transaction.builder().account(account2).amount(new BigDecimal("54.70")).discriminator(Transaction.TransactionType.CREDIT)
                .build();
        Transaction a3 = Transaction.builder().account(account3).amount(new BigDecimal("54.70"))
                .discriminator(Transaction.TransactionType.DEBIT).build();

        list.add(a1);
        list.add(a2);
        list.add(a3);

        when(txRepo.findAll()).thenReturn(list);

        // test
        List<Transaction> empList = txService.getTransactionHistory();

        assertEquals(3, empList.size());
    }

    @Test
    public void accountTransferA1toA2Test() {

        when(accountRepo.findByAccountNumber(account1.getAccountNumber())).thenReturn(account1);
        when(accountRepo.findByAccountNumber(account2.getAccountNumber())).thenReturn(account2);
        Mockito.doNothing().when(entityManager).refresh(Mockito.any());

        Mockito.when(accountRepo.save(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);

        // replaced mocked entity manager using reflection
        ReflectionTestUtils.setField(txService, "entityManager", entityManager);

        BigDecimal a1InitialBalance = account1.getBalance();
        BigDecimal a2InitialBalance = account2.getBalance();

        // test
        txService.transfer(account1.getAccountNumber(), account2.getAccountNumber(),
                new BigDecimal("10.00"));

        assertEquals(a2InitialBalance.add(new BigDecimal("10.00")), account2.getBalance());
        assertEquals(a1InitialBalance.subtract(new BigDecimal("10.00")), account1.getBalance());

    }

    @Test
    public void withdrawTest() {

        when(accountRepo.findByAccountNumber(account1.getAccountNumber())).thenReturn(account1);
        Mockito.doNothing().when(entityManager).refresh(Mockito.any());

        Mockito.when(accountRepo.save(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);

        // replaced mocked entity manager using reflection
        ReflectionTestUtils.setField(txService, "entityManager", entityManager);

        BigDecimal a1InitialBalance = account1.getBalance();

        // test
        txService.withdrawal(account1.getAccountNumber(), new BigDecimal("10.00"));

        assertEquals(a1InitialBalance.subtract(new BigDecimal("10.00")), account1.getBalance());

    }

}
