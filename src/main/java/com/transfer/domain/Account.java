package com.transfer.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Account")
public class Account {

    @Id
    @GeneratedValue
    @Column(name="accountNumber")
    @JsonProperty("account_number")
    private Integer accountNumber;

    @Column(name="balance")
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


}
