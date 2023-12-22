package com.hendisantika.abcbank.service;

import com.hendisantika.abcbank.repository.TransactionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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


}
