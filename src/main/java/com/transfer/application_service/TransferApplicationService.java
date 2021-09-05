package com.transfer.application_service;

import com.transfer.data_service.AccountDataService;
import com.transfer.data_transfer.AccountDto;
import com.transfer.data_transfer.TransferDto;
import com.transfer.domain.Account;
import com.transfer.exception.AccountNotFoundException;
import com.transfer.exception.InvalidDataException;
import com.transfer.mapper.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
public class TransferApplicationService {

    public static Logger log = LoggerFactory.getLogger(TransferApplicationService.class);

    private AccountDataService accountDataService;
    private Mapper mapper;

    public TransferApplicationService(AccountDataService accountDataService, Mapper mapper) {
        this.accountDataService = accountDataService;
        this.mapper = mapper;
    }

    @Transactional
    public AccountDto transferAmount(TransferDto transferDTO) {

        validateTransferDTO(transferDTO);
        Account destinationAccount = accountDataService
                .findByAccountNumber(transferDTO.getDestinationAccountNumber());
        Account sourceAccount = accountDataService
                .findByAccountNumber(transferDTO.getSourceAccountNumber());
        if (Objects.isNull(destinationAccount) || Objects.isNull(sourceAccount)) {
            throw new AccountNotFoundException("Account Number not found");
        }
        if (sourceAccount.getBalance() - transferDTO.getAmount() < 0) {
            throw new InvalidDataException("Not Enough Funds");
        }
        double newBalanceOfDestinationAccount = destinationAccount.getBalance() + transferDTO.getAmount();
        destinationAccount.setBalance(newBalanceOfDestinationAccount);

        double newBalanceOfSourceAccount = sourceAccount.getBalance() - transferDTO.getAmount();
        sourceAccount.setBalance(newBalanceOfSourceAccount);
        accountDataService.save(sourceAccount);
        accountDataService.save(destinationAccount);
        return mapper.mapToAccountDto(sourceAccount);

    }


    public void validateTransferDTO(TransferDto transferDTO) {

        if (Objects.isNull(transferDTO.getSourceAccountNumber()) ||
                Objects.isNull(transferDTO.getDestinationAccountNumber())) {
            throw new InvalidDataException("Account number cannot be empty");
        }
        if (Objects.isNull(transferDTO.getAmount()) || transferDTO.getAmount() <= 0) {
            throw new InvalidDataException("Balance must be greater then zero");
        }
    }
}
