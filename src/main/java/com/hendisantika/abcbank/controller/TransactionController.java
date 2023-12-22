package com.hendisantika.abcbank.controller;

import com.hendisantika.abcbank.entity.Account;
import com.hendisantika.abcbank.model.DepositToAccountModel;
import com.hendisantika.abcbank.model.TransferToAccountModel;
import com.hendisantika.abcbank.model.WithdrawFromAccountModel;
import com.hendisantika.abcbank.service.AccountTransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Get Success",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Account.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Account.class))}),
            @ApiResponse(responseCode = "404",
                    description = "Not Found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Account.class))}),
            @ApiResponse(responseCode = "503",
                    description = "Service Unavailable",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Account.class))})
    })
    @PostMapping(value = "/withdraw")
    public ResponseEntity<?> withdraw(@Valid @RequestBody WithdrawFromAccountModel reqModel) {
        txService.withdrawal(reqModel.getFromAccountNumber(), reqModel.getWithdrawlAmount());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "deposit amount")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Get Success",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Account.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Account.class))}),
            @ApiResponse(responseCode = "404",
                    description = "Not Found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Account.class))}),
            @ApiResponse(responseCode = "503",
                    description = "Service Unavailable",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Account.class))})
    })
    @PostMapping(value = "/deposit")
    public ResponseEntity<?> deposit(@Valid @RequestBody DepositToAccountModel reqModel) {
        txService.deposit(reqModel.getDepositorAccountNumber(), reqModel.getDepositAmount());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Operation(summary = "transfer amount to another account ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Get Success",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Account.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Account.class))}),
            @ApiResponse(responseCode = "404",
                    description = "Not Found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Account.class))}),
            @ApiResponse(responseCode = "503",
                    description = "Service Unavailable",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Account.class))})
    })
    @PostMapping(value = "/transfer")
    public ResponseEntity<?> transfer(@Valid @RequestBody TransferToAccountModel requestModel) {
        txService.transfer(requestModel.getFromAccountNumber(), requestModel.getToAccountNumber(),
                requestModel.getTransferAmount());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
