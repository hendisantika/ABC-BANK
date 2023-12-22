package com.hendisantika.abcbank.controller;

import com.hendisantika.abcbank.model.WithdrawFromAccountModel;
import com.hendisantika.abcbank.service.AccountTransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by IntelliJ IDEA.
 * Project : ABC-BANK
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 12/22/23
 * Time: 09:36
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequestMapping(produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
@Tag(name = "TRANSACTION", description = "Operations on Transaction")
@Validated
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
public class TransactionController {

    private final AccountTransactionService txService;

    private final ModelMapper modelMapper;

    @Operation(summary = "withdraw amount")
    @PostMapping(value = "/withdraw")
    public ResponseEntity<?> withdraw(@Valid @RequestBody WithdrawFromAccountModel reqModel) {
        txService.withdrawal(reqModel.getFromAccountNumber(), reqModel.getWithdrawlAmount());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
