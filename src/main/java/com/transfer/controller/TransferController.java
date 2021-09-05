package com.transfer.controller;

import com.transfer.application_service.TransferApplicationService;
import com.transfer.data_transfer.AccountDto;
import com.transfer.data_transfer.TransferDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransferController {

    private TransferApplicationService transferApplicationService;

    public TransferController(TransferApplicationService transferApplicationService) {
        this.transferApplicationService = transferApplicationService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transferAmount(@RequestBody TransferDto transferDTO) {
        AccountDto sourceAccountDto = transferApplicationService.transferAmount(transferDTO);
        return ResponseEntity.ok(sourceAccountDto);
    }
}


