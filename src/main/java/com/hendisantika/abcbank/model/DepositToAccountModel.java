package com.hendisantika.abcbank.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * Project : ABC-BANK
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 12/22/23
 * Time: 09:20
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
public class DepositToAccountModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank
    private String depositorAccountNumber;

    @NotNull
    private BigDecimal depositAmount;
}
