package com.hendisantika.abcbank.config;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
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
        synchronized (this) {
            accLock = accountLockMap.get(accountNumber);
            if (accLock == null) {
                AccountLock newLock = new AccountLock(new ReentrantLock(), 0);
                accountLockMap.put(accountNumber, newLock);
                accLock = newLock;
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

    public void unlock(String accountNumber) {
        AccountLock accLock = accountLockMap.get(accountNumber);
        if (accLock != null) {
            int lockCount = accLock.lockCount.decrementAndGet();
            accLock.lock.unlock();
            /*
             * if count zero take lock and insure count is still zero (no one acquire lock after unlock call)
             */
            if (lockCount == 0) {
                synchronized (this) {
                    lockCount = accLock.lockCount.get();
                    if (lockCount == 0) {
                        accountLockMap.remove(accountNumber);
                    }
                }
            }
        }
    }

    public boolean tryLock(String accountNumber) {
        AccountLock accLock = accountLockMap.get(accountNumber);
        synchronized (this) {
            accLock = accountLockMap.get(accountNumber);
            if (accLock == null) {
                AccountLock newLock = new AccountLock(new ReentrantLock(), 0);
                accountLockMap.put(accountNumber, newLock);
                accLock = newLock;
            }
        }

        boolean isAcquired = accLock.lock.tryLock();
        if (isAcquired) {
            synchronized (this) {
                accLock.lockCount.incrementAndGet();
                if (null == accountLockMap.get(accountNumber)) {
                    accountLockMap.put(accountNumber, accLock);
                }
            }
        }

        return isAcquired;
    }

    private static class AccountLock {
        private final ReentrantLock lock;

        private final AtomicInteger lockCount;

        public AccountLock(ReentrantLock lock, int initialValue) {
            this.lock = lock;
            lockCount = new AtomicInteger(initialValue);
        }
    }
}
