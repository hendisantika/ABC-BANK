package com.hendisantika.abcbank.service;


import com.hendisantika.abcbank.entity.Account;
import com.hendisantika.abcbank.repository.AccountRepository;
import com.hendisantika.abcbank.util.AppUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : ABC-BANK
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 12/22/23
 * Time: 09:24
 * To change this template use File | Settings | File Templates.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    @Transactional
    public Account saveUpdate(Account entity) {
        return accountRepository.save(entity);
    }

    public Account findByAccountNumber(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber);
        if (AppUtil.isNullObject(account)) {
            String errMessage = String.format("No entity found for Account# %s", accountNumber);
            log.error(errMessage);
            throw new EntityNotFoundException(errMessage);
        }
        return account;
    }
}
