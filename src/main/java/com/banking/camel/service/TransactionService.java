package com.banking.camel.service;


import com.banking.camel.model.TransactionHistoryResponse;
import com.banking.camel.model.TransactionRecord;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

import java.util.List;

@WebService
public interface TransactionService {


    @WebMethod
    TransactionHistoryResponse getTransactionHistory(
            @WebParam(name = "accountNumber") String accountNumber
    );
}