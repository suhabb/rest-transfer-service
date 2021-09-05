package com.transfer.application_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transfer.data_service.AccountDataService;
import com.transfer.data_transfer.AccountDto;
import com.transfer.data_transfer.TransferDto;
import com.transfer.domain.Account;
import com.transfer.exception.AccountNotFoundException;
import com.transfer.exception.InvalidDataException;
import com.transfer.mapper.Mapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransferApplicationServiceTest {

    private AccountDataService accountDataService = mock(AccountDataService.class);
    private TransferApplicationService transferApplicationService;

    @BeforeEach
    public void setUp(){
        Mapper mapper = new Mapper(new ObjectMapper());
        this.transferApplicationService = new TransferApplicationService(accountDataService,mapper);
    }

    @Test
    public void given_valid_input_of_transfer_check_source_account_balance(){
        TransferDto transferDTO = new TransferDto.TransferDtoBuilder()
                .setAmount(20.00)
                .setDestinationAccountNumber(321234)
                .setSourceAccountNumber(52313)
                .build();

        Account sourceAccount = new Account();
        sourceAccount.setBalance(100.00);
        sourceAccount.setAccountNumber(52313);

        Account destinationAccount = new Account();
        destinationAccount.setBalance(40.00);
        destinationAccount.setAccountNumber(321234);

        when(accountDataService.findByAccountNumber(anyInt()))
                .thenReturn(destinationAccount)
                .thenReturn(sourceAccount);
        when(accountDataService.save(any()))
                .thenReturn(sourceAccount)
                .thenReturn(destinationAccount);
        AccountDto actualSourceAccount = transferApplicationService.transferAmount(transferDTO);
        Assertions.assertEquals(80.00,actualSourceAccount.getBalance());
    }

    @Test
    public void given_invalid_account_number_throws_InvalidDataException(){
        TransferDto transferDTO = new TransferDto.TransferDtoBuilder()
                .setAmount(20.00)
                .setDestinationAccountNumber(null)
                .setSourceAccountNumber(52313)
                .build();

        String expectedMessage = "Account number cannot be empty";
        InvalidDataException invalidDataException = Assertions.assertThrows(InvalidDataException.class,
                () -> transferApplicationService.transferAmount(transferDTO));
        Assertions.assertTrue(invalidDataException.getMessage().contains(expectedMessage));
    }

    @Test
    public void given_invalid_amount_throws_InvalidDataException(){
        TransferDto transferDTO = new TransferDto.TransferDtoBuilder()
                .setAmount(null)
                .setDestinationAccountNumber(23434)
                .setSourceAccountNumber(52313)
                .build();

        String expectedMessage = "Balance must be greater then zero";
        InvalidDataException invalidDataException = Assertions.assertThrows(InvalidDataException.class,
                () -> transferApplicationService.transferAmount(transferDTO));
        Assertions.assertTrue(invalidDataException.getMessage().contains(expectedMessage));
    }

    @Test
    public void given_invalid_negative_amount_test_throws_InvalidDataException(){
        TransferDto transferDTO = new TransferDto.TransferDtoBuilder()
                .setAmount(-20.00)
                .setDestinationAccountNumber(23434)
                .setSourceAccountNumber(52313)
                .build();

        String expectedMessage = "Balance must be greater then zero";
        InvalidDataException invalidDataException = Assertions.assertThrows(InvalidDataException.class,
                () -> transferApplicationService.transferAmount(transferDTO));
        Assertions.assertTrue(invalidDataException.getMessage().contains(expectedMessage));
    }

    @Test
    public void given_account_number_not_found_AccountNotFoundException(){
        TransferDto transferDTO = new TransferDto.TransferDtoBuilder()
                .setAmount(20.00)
                .setDestinationAccountNumber(11111)
                .setSourceAccountNumber(22222)
                .build();
        when(accountDataService.findByAccountNumber(anyInt()))
                .thenReturn(null)
                .thenReturn(null);

        String expectedMessage = "Account Number not found";
        AccountNotFoundException accountNotFoundException = Assertions.assertThrows(AccountNotFoundException.class,
                () -> transferApplicationService.transferAmount(transferDTO));
        Assertions.assertTrue(accountNotFoundException.getMessage().contains(expectedMessage));
    }

    @Test
    public void given_transfer_throws_InvalidDataException_when_funds_not_enough(){
        TransferDto transferDTO = new TransferDto.TransferDtoBuilder()
                .setAmount(120.00)
                .setDestinationAccountNumber(23434)
                .setSourceAccountNumber(52313)
                .build();

        Account sourceAccount = new Account();
        sourceAccount.setBalance(100.00);
        sourceAccount.setAccountNumber(52313);

        Account destinationAccount = new Account();
        destinationAccount.setBalance(40.00);
        destinationAccount.setAccountNumber(321234);

        when(accountDataService.findByAccountNumber(anyInt()))
                .thenReturn(sourceAccount)
                .thenReturn(destinationAccount);

        String expectedMessage = "Not Enough Funds";
        InvalidDataException invalidDataException = Assertions.assertThrows(InvalidDataException.class,
                () -> transferApplicationService.transferAmount(transferDTO));
        Assertions.assertTrue(invalidDataException.getMessage().contains(expectedMessage));
    }
}
