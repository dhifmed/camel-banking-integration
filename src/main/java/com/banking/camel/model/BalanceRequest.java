package com.banking.camel.model;

import lombok.Data;

@Data
public class BalanceRequest {
    public String accountNumber;

    @Override
    public String toString() {
        return "BalanceRequest{" +
                "accountNumber='" + accountNumber + '\'' +
                '}';
    }
}
