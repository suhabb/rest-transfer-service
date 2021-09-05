package com.transfer.data_transfer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDto {

    @JsonProperty("account_number")
    private Integer accountNumber;

    @JsonProperty("balance")
    private Double balance;

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    private AccountDto(){}

    public AccountDto(AccountDto.AccountDtoBuilder accountDtoBuilder) {
        this.accountNumber = accountDtoBuilder.accountNumber;
        this.balance = accountDtoBuilder.balance;

    }

    public static class AccountDtoBuilder {

        private Integer accountNumber;

        private Double balance;

        public AccountDto.AccountDtoBuilder setAccountNumber(Integer accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        public AccountDto.AccountDtoBuilder setBalance(Double balance) {
            this.balance = balance;
            return this;
        }

        public AccountDto build() {
            return new AccountDto(this);
        }
    }
}
