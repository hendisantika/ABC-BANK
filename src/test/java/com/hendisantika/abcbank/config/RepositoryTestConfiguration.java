package com.hendisantika.abcbank.config;

import com.hendisantika.abcbank.repository.AccountRepository;
import com.hendisantika.abcbank.repository.TransactionRepository;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

/**
 * Created by IntelliJ IDEA.
 * Project : ABC-BANK
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 12/23/23
 * Time: 05:46
 * To change this template use File | Settings | File Templates.
 */
@Profile("test")
@Configuration
public class RepositoryTestConfiguration {

    @Bean
    @Primary
    public AccountRepository accountRepo() {
        return Mockito.mock(AccountRepository.class);
    }

    @Bean
    @Primary
    public TransactionRepository txRepository() {
        return Mockito.mock(TransactionRepository.class);
    }
}
