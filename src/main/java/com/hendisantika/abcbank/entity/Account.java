package com.hendisantika.abcbank.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : ABC-BANK
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 12/22/23
 * Time: 08:33
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
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "account_number")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_number_gen_seq")
    @GenericGenerator(name = "account_number_gen_seq", strategy = "com.abcbank.accountmaintenance.entity.seqgenerator.StringSequenceIdentifier", parameters = {
            @Parameter(name = StringSequenceIdentifier.INCREMENT_PARAM, value = "1"),
            @Parameter(name = StringSequenceIdentifier.VALUE_PREFIX_PARAMETER, value = "ABC_"),
            @Parameter(name = StringSequenceIdentifier.NUMBER_FORMAT_PARAMETER, value = "%010d")})

    @EqualsAndHashCode.Include
    @Schema(hidden = true)
    private String accountNumber;

    @Column(name = "account_holder")
    private String acountHolder;

    @Column(name = "balance", precision = 19, scale = 2, columnDefinition = "DECIMAL(19,2)")
    @Type(type = "java.math.BigDecimal")
    private BigDecimal balance;

    @Temporal(TemporalType.DATE)
    @Builder.Default
    private Date createdDate = new Date();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    @org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.EXTRA)
    @JsonIgnore
    @ToString.Exclude
    private List<Transaction> transactions;
}
