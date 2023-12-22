package com.hendisantika.abcbank.controller;

import com.hendisantika.abcbank.service.AccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
