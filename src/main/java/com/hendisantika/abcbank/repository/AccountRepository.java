package com.hendisantika.abcbank.repository;

import com.hendisantika.abcbank.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by IntelliJ IDEA.
 * Project : ABC-BANK
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 12/22/23
 * Time: 09:23
 * To change this template use File | Settings | File Templates.
 */
@Repository
public interface AccountRepository
        extends JpaRepository<Account, String>, CrudRepository<Account, String>, JpaSpecificationExecutor<Account> {

    Account findByAccountNumber(String accountNumber);
}
