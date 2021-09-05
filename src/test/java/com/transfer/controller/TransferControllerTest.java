package com.transfer.controller;

import com.transfer.application_service.TransferApplicationService;
import com.transfer.data_transfer.AccountDto;
import com.transfer.data_transfer.TransferDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransferControllerTest {

    private TransferApplicationService transferApplicationService = mock(TransferApplicationService.class);

    private TransferController transferController;

    @BeforeEach
    public void setUp(){
        transferController = new TransferController(transferApplicationService);
    }

    @Test
    public void given_uri_return_response_status_ok(){
        TransferDto transferDTO = new TransferDto.TransferDtoBuilder()
                .setAmount(20.00)
                .setDestinationAccountNumber(321234)
                .setSourceAccountNumber(52313)
                .build();

         AccountDto sourceAccount = new AccountDto.AccountDtoBuilder()
                .setAccountNumber(52313)
                .setBalance(80.00)
                 .build();
        when(transferApplicationService.transferAmount(transferDTO)).thenReturn(sourceAccount);
        ResponseEntity<?> accountDto = transferController.transferAmount(transferDTO);
        ResponseEntity expectedResponse = ResponseEntity.ok(accountDto);
        Assertions.assertEquals(expectedResponse.getStatusCode(),accountDto.getStatusCode());

    }
}
