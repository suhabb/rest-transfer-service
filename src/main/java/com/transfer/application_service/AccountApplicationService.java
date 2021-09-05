package com.transfer.application_service;

import com.transfer.data_service.AccountDataService;
import com.transfer.data_transfer.AccountDto;
import com.transfer.domain.Account;
import com.transfer.exception.AccountNotFoundException;
import com.transfer.mapper.Mapper;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AccountApplicationService {

    private AccountDataService accountDataService;

    private Mapper mapper;

    public AccountApplicationService(AccountDataService accountDataService, Mapper mapper){
        this.accountDataService = accountDataService;
        this.mapper= mapper;
    }
    public AccountDto findByAccountNumber(Integer accountNumber){
        Account account = accountDataService.findByAccountNumber(accountNumber);
        if(Objects.isNull(account)){
            throw new AccountNotFoundException("Account Not Found");
        }
        return  mapper.mapToAccountDto(account);
    }
}
