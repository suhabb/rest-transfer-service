package com.transfer.data_transfer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransferDto {

    @JsonProperty("sourceAccountNumber")
    private Integer sourceAccountNumber;

    @JsonProperty("destinationAccountNumber")
    private Integer destinationAccountNumber;

    @JsonProperty("amount")
    private Double amount;

    private TransferDto(){}

    public Integer getSourceAccountNumber() {
        return sourceAccountNumber;
    }

    public void setSourceAccountNumber(Integer sourceAccountNumber) {
        this.sourceAccountNumber = sourceAccountNumber;
    }

    public Integer getDestinationAccountNumber() {
        return destinationAccountNumber;
    }

    public void setDestinationAccountNumber(Integer destinationAccountNumber) {
        this.destinationAccountNumber = destinationAccountNumber;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public TransferDto(TransferDtoBuilder transferDtoBuilder) {
        this.sourceAccountNumber = transferDtoBuilder.sourceAccountNumber;
        this.destinationAccountNumber = transferDtoBuilder.destinationAccountNumber;
        this.amount = transferDtoBuilder.amount;
    }

    public static class TransferDtoBuilder {

        private Integer sourceAccountNumber;

        private Integer destinationAccountNumber;

        private Double amount;

        public TransferDtoBuilder setSourceAccountNumber(Integer sourceAccountNumber) {
            this.sourceAccountNumber = sourceAccountNumber;
            return this;
        }

        public TransferDtoBuilder setDestinationAccountNumber(Integer destinationAccountNumber) {
            this.destinationAccountNumber = destinationAccountNumber;
            return this;
        }

        public TransferDtoBuilder setAmount(Double amount) {
            this.amount = amount;
            return this;
        }

        public TransferDto build() {
            return new TransferDto(this);
        }
    }
}
