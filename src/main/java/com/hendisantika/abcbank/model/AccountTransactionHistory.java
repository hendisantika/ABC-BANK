package com.hendisantika.abcbank.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hendisantika.abcbank.entity.Transaction;
import lombok.*;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Project : ABC-BANK
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 12/22/23
 * Time: 09:19
 * To change this template use File | Settings | File Templates.
 */
@Builder
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.ALWAYS)
@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AccountTransactionHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    private String accountNumber;

    private BigDecimal amount;

    private Date createdDate;

    public static Converter<Transaction, AccountTransactionHistory> converter() {
        return (MappingContext<Transaction, AccountTransactionHistory> context) -> {
            Transaction source = context.getSource();
            AccountTransactionHistory destination = new AccountTransactionHistory();
            destination.setAccountNumber(source.getAccount().getAccountNumber());
            BigDecimal amount1 = source.getAmount();
            if (null != amount1) {
                BigDecimal scalledAmount = amount1.setScale(2, RoundingMode.HALF_UP);
                destination.setAmount(
                        source.getDiscriminator() == Transaction.TransactionType.CREDIT ? scalledAmount : scalledAmount.negate());
            }
            destination.setCreatedDate(source.getAccount().getCreatedDate());
            return destination;
        };
    }
}
