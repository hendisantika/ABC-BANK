package com.hendisantika.abcbank.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hendisantika.abcbank.entity.seqgenerator.StringSequenceIdentifier;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Parameter;
import org.hibernate.type.SqlTypes;

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
@JsonInclude()
@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "account")
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "account_number")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_number_gen_seq")
    @GenericGenerator(name = "account_number_gen_seq", type = StringSequenceIdentifier.class, parameters = {
            @Parameter(name = StringSequenceIdentifier.INCREMENT_PARAM, value = "1"),
            @Parameter(name = StringSequenceIdentifier.VALUE_PREFIX_PARAMETER, value = "ABC_"),
            @Parameter(name = StringSequenceIdentifier.NUMBER_FORMAT_PARAMETER, value = "%010d")})

    @EqualsAndHashCode.Include
    @Schema(hidden = true)
    private String accountNumber;

    @Column(name = "account_holder")
    private String acountHolder;

    @Column(name = "balance", precision = 19, columnDefinition = "DECIMAL(19,2)")
    @JdbcTypeCode(SqlTypes.DOUBLE)
    private BigDecimal balance;

    @Temporal(TemporalType.DATE)
    @Builder.Default
    private Date createdDate = new Date();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)    
    @JsonIgnore
    @ToString.Exclude
    private List<Transaction> transactions;
}
