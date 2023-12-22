package com.hendisantika.abcbank.service;

import com.hendisantika.abcbank.config.TransactionLock;
import com.hendisantika.abcbank.entity.Account;
import com.hendisantika.abcbank.entity.Transaction;
import com.hendisantika.abcbank.repository.TransactionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * Project : ABC-BANK
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 12/22/23
 * Time: 09:25
 * To change this template use File | Settings | File Templates.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AccountTransactionService {
    private final AccountService accountService;

    private final TransactionRepository transactionRepository;
    private final TransactionLock txLock;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void withdrawal(String accountNumber, BigDecimal amount) {
        Account account = accountService.findByAccountNumber(accountNumber);
        // check sufficient balance before accruing lock: throw validation exception.
        insureBalance(amount, account);

        try {
            txLock.lock(accountNumber);
            // reload entity after lock - no other transaction performing transfer or withdraw at this point
            this.refreshEntity(account);
            // check sufficient balance after accruing lock - may be previous tx has already withdraw some amount
            insureBalance(amount, account);

            account.setBalance(account.getBalance().subtract(amount));
            Transaction tx = Transaction.builder().account(account).amount(amount).discriminator(Transaction.TransactionType.DEBIT).build();
            account.getTransactions().add(tx);

            accountService.saveUpdate(account);
        } finally {
            txLock.unlock(accountNumber);
        }
    }

    @Transactional
    public void transfer(String fromAccountNumber, String toAccountNumber, BigDecimal amount) {
        //order the count to prevent deadlock (a,b) then (b,c) then (c,a) OR (a,b) then (b,a)
        String lockSmallestFirst = fromAccountNumber;
        String lockgigerNest = toAccountNumber;
        if (fromAccountNumber.compareTo(toAccountNumber) > 0) {
            lockSmallestFirst = toAccountNumber;
            lockgigerNest = fromAccountNumber;
        }
        try {
            txLock.lock(lockSmallestFirst);
            txLock.lock(lockgigerNest);

            Account fromAccount = accountService.findByAccountNumber(fromAccountNumber);
            // check sufficient balance after accruing lock - may be previous tx has already withdraw some amount
            insureBalance(amount, fromAccount);

            BigDecimal subtractedAmt = fromAccount.getBalance().subtract(amount);
            fromAccount.setBalance(subtractedAmt);
            Transaction txDebit = Transaction.builder().account(fromAccount).amount(amount).discriminator(Transaction.TransactionType.DEBIT).build();
            fromAccount.getTransactions().add(txDebit);

            accountService.saveUpdate(fromAccount);

            this.depositToAccount(toAccountNumber, amount);
        } finally {
            txLock.unlock(lockgigerNest);
            txLock.unlock(lockSmallestFirst);
        }
    }

    @Transactional
    public Account deposit(String accountNumber, BigDecimal amount) {
        try {
            txLock.lock(accountNumber);
            return this.depositToAccount(accountNumber, amount);
        } finally {
            txLock.unlock(accountNumber);
        }
    }

}
