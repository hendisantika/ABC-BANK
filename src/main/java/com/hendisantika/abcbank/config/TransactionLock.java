package com.hendisantika.abcbank.config;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

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

    public void lock(String accountNumber) {
        AccountLock accLock = accountLockMap.get(accountNumber);
        // double locking : to initialize the lock object
        if (accLock == null) {
            synchronized (this) {
                accLock = accountLockMap.get(accountNumber);
                if (accLock == null) {
                    AccountLock newLock = new AccountLock(new ReentrantLock(), 0);
                    accountLockMap.put(accountNumber, newLock);
                    accLock = newLock;
                }
            }

        }
        synchronized (this) {
            accLock.lockCount.incrementAndGet();
            // if other concurrent tx has removed from map using unlock- up it back
            if (null == accountLockMap.get(accountNumber)) {
                accountLockMap.put(accountNumber, accLock);
            }
        }

        accLock.lock.lock();
    }

}
