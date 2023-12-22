package com.hendisantika.abcbank.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hendisantika.abcbank.entity.Transaction;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Project : ABC-BANK
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 12/22/23
 * Time: 09:21
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
public class TransactionHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    private String accountNumber;

    private BigDecimal withdrawl;

    private BigDecimal deposit;

    private Date transactionDate;


    public static TransactionHistory converter(Date txDate, String accountNumber,
                                               Map<Transaction.TransactionType, BigDecimal> descriminatorSum) {
        TransactionHistory destination = new TransactionHistory();
        destination.setAccountNumber(accountNumber);
        destination.setDeposit(
                descriminatorSum.get(Transaction.TransactionType.CREDIT) != null
                        ? descriminatorSum.get(Transaction.TransactionType.CREDIT).setScale(2, RoundingMode.HALF_UP)
                        : descriminatorSum.get(Transaction.TransactionType.CREDIT));
        BigDecimal debitAmount = descriminatorSum.get(Transaction.TransactionType.DEBIT);
        destination.setWithdrawl(debitAmount != null ? debitAmount.setScale(2, RoundingMode.HALF_UP).negate() : debitAmount);
        destination.setTransactionDate(txDate);
        return destination;
    }
}
