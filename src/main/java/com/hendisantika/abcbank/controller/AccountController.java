package com.hendisantika.abcbank.controller;

import com.hendisantika.abcbank.entity.Account;
import com.hendisantika.abcbank.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : ABC-BANK
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 12/22/23
 * Time: 09:32
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequestMapping(value = "/accounts", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
@Tag(name = "ACCOUNT", description = "Operations on Bank")
@Validated
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @Operation(summary = "get all accounts ")
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
    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = accountService.getAccounts();
        return ResponseEntity.ok(accounts);
    }
}
