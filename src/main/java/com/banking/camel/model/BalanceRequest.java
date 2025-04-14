package com.banking.camel.model;

public class BalanceRequest {
    public String accountNumber;

    @Override
    public String toString() {
        return "BalanceRequest{" +
                "accountNumber='" + accountNumber + '\'' +
                '}';
    }
}
