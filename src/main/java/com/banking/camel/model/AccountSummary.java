package com.banking.camel.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class AccountSummary {

    Double balance;
    List<TransactionRecord> transactionRecordList;
}
