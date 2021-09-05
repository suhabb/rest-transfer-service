package com.transfer.data_service;

import com.transfer.domain.Account;
import com.transfer.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountDataService {

    private AccountRepository accountRepository;

    public AccountDataService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account findByAccountNumber(Integer accountNumber) {
        return this.accountRepository.findByAccountNumber(accountNumber);
    }
    public Account save(Account account) {
        return this.accountRepository.save(account);
    }
}
