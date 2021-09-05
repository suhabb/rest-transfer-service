package com.transfer.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transfer.data_transfer.AccountDto;
import com.transfer.domain.Account;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class Mapper {

    private ObjectMapper objectMapper;

    public Mapper(ObjectMapper mapper) {
        this.objectMapper = mapper;
    }

    public AccountDto mapToAccountDto(Account account) {
        try {
            return objectMapper.readValue(objectMapper.writeValueAsString(account),
                    AccountDto.class);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
