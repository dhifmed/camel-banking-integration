package com.banking.camel.service;


import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

@WebService(targetNamespace = "http://banking.integration.com/")
public interface BankingService {

    @WebMethod
    double getBalance(@WebParam(name = "accountNumber") String accountNumber);


}