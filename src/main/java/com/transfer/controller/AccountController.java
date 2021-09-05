package com.transfer.controller;

import com.transfer.application_service.AccountApplicationService;
import com.transfer.data_transfer.AccountDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    private AccountApplicationService accountApplicationService;

    public AccountController(AccountApplicationService accountApplicationService) {
        this.accountApplicationService = accountApplicationService;
    }

    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<AccountDto> findAccountNumber(@PathVariable Integer accountNumber) {
        AccountDto accountDTO = accountApplicationService.findByAccountNumber(accountNumber);
        return ResponseEntity.ok(accountDTO);
    }
}
