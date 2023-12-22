package com.hendisantika.abcbank.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hendisantika.abcbank.entity.seqgenerator.StringSequenceIdentifier;
import com.hendisantika.abcbank.util.AppUtil;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Project : ABC-BANK
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 12/22/23
 * Time: 08:36
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "transaction")

@Builder
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.ALWAYS)
@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_number_gen_seq")
    @GenericGenerator(name = "transaction_number_gen_seq", strategy = "com.hendisantika.abcbank.entity.seqgenerator.StringSequenceIdentifier", parameters = {
            @Parameter(name = StringSequenceIdentifier.INCREMENT_PARAM, value = "1"),
            @Parameter(name = StringSequenceIdentifier.VALUE_PREFIX_PARAMETER, value = "TX_"),
            @Parameter(name = StringSequenceIdentifier.NUMBER_FORMAT_PARAMETER, value = "%010d")})

    @Column(name = "transaction_id")
    @EqualsAndHashCode.Include
    private String transactionId;

    @Column(name = "amount")
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "discriminator", nullable = false)
    private TransactionType discriminator;

    @Temporal(TemporalType.TIMESTAMP)
    @Builder.Default
    private Date transactionDate = new Date();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_number", updatable = false)
    private Account account;

    public String getAccountNumber() {
        return this.account.getAccountNumber();
    }

    public Date getTxDateWithoutTime() {
        return AppUtil.truncateDate(transactionDate);
    }

    public enum TransactionType {
        DEBIT,
        CREDIT,
    }
}
