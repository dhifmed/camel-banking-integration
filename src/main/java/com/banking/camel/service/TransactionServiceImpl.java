package com.banking.camel.service;


import com.banking.camel.model.TransactionHistoryResponse;
import com.banking.camel.model.TransactionRecord;
import jakarta.jws.WebService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@WebService(
        endpointInterface = "com.banking.camel.service.TransactionService",
        serviceName = "TransactionService"
)
@Component
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    // Static list simulating persistent transaction storage
    private static final List<TransactionRecord> records = new ArrayList<>();

    static {
        // Sample data for testing
        TransactionRecord r1 = new TransactionRecord("TXN001", "ACC123", "ACC456", 100.0, "USD", LocalDateTime.now().minusDays(2));
        TransactionRecord r2 = new TransactionRecord("TXN002", "ACC456", "ACC789", 250.5, "USD", LocalDateTime.now().minusDays(1));
        TransactionRecord r3 = new TransactionRecord("TXN003", "ACC123", "ACC789", 75.0, "EUR", LocalDateTime.now());


        records.add(r1);
        records.add(r2);
        records.add(r3);
    }

    @Override
    public TransactionHistoryResponse getTransactionHistory(String accountNumber) {
        List<TransactionRecord> history = new ArrayList<>();
        for (TransactionRecord r : records) {
            if (r.fromAccount.equals(accountNumber) || r.toAccount.equals(accountNumber)) {
                history.add(r);
            }
        }
        log.info("TRANSACTION RECORDS  --- {}", history);
        TransactionHistoryResponse transactionHistoryResponse = new TransactionHistoryResponse(history);
        log.info("TRANSACTION HISTORY --- {}", transactionHistoryResponse);
        return transactionHistoryResponse;
    }
}
