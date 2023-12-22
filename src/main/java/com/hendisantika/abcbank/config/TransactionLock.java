package com.hendisantika.abcbank.config;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by IntelliJ IDEA.
 * Project : ABC-BANK
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 12/22/23
 * Time: 09:27
 * To change this template use File | Settings | File Templates.
 */
@Component
public class TransactionLock {

    private Map<String, AccountLock> accountLockMap;

    @PostConstruct
    public void init() {
        accountLockMap = new ConcurrentHashMap<>();
    }
}
