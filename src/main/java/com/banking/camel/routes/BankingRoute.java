package com.banking.camel.routes;

import com.banking.camel.model.AccountSummary;
import com.banking.camel.model.BalanceRequest;
import com.banking.camel.model.TransactionHistoryResponse;
import com.banking.camel.model.TransactionRecord;
import com.banking.camel.service.TransactionServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class BankingRoute extends RouteBuilder {


    @Autowired
    TransactionServiceImpl transactionService;

    private final TransactionServiceImpl transactionServiceImpl;
    private final AccountSummary accountSummary;


    public BankingRoute(TransactionServiceImpl transactionServiceImpl, AccountSummary accountSummary) {

        this.transactionServiceImpl = transactionServiceImpl;
        this.accountSummary = accountSummary;
    }

    @Override
    public void configure() throws Exception {
        System.out.println("CAMEL RUNNING -------");

        restConfiguration()
                .component("netty-http")
                .port(8080)
                .bindingMode(RestBindingMode.json);

        rest("/balance")
                .post()
                .type(BalanceRequest.class)
                .outType(String.class)
                .to("direct:getBalanceAndTransactionHistory");

        from("direct:getBalanceAndTransactionHistory")
                .process(exchange -> {
                    BalanceRequest request = exchange.getIn().getBody(BalanceRequest.class);
                    exchange.setProperty("accountNumber", request.getAccountNumber());
                })
                .log("account number received : ${body}")
                .to("direct:getBalance")
                .log("balance received : ${body}")
                .to("direct:getTransactions")
                .log("transaction history received : ${body}")
                .process(exchange -> {
                    Double balance = exchange.getProperty("balance", Double.class);
                    @SuppressWarnings("unchecked")
                    List<TransactionRecord> txList = exchange.getProperty("transactions", List.class);


                    accountSummary.setBalance(balance);
                    accountSummary.setTransactionRecordList(txList);
                    exchange.getMessage().setBody(accountSummary);


                });


        from("direct:getBalance")
                .process(exchange -> {
                    String acc = exchange.getProperty("accountNumber", String.class);
                    exchange.getIn().setBody(acc);
                })
                .to("cxf://http://localhost:8081/banking"
                        + "?serviceClass=com.banking.camel.service.BankingService"
                        + "&defaultOperationName=getBalance"
                        + "&dataFormat=POJO")
                .process(exchange -> {
                    Double balance = exchange.getIn().getBody(Double.class);
                    exchange.setProperty("balance", balance);
                });

        from("direct:getTransactions")
                .process(exchange -> {
                    String acc = exchange.getProperty("accountNumber", String.class);
                    exchange.getIn().setBody(acc);
                })
                .log("request with account number : ${body}")
                .to("cxf:/TransactionService"
                        + "?serviceClass=com.banking.camel.service.TransactionService"
                        + "&address=http://localhost:8083/transactions"
                        + "&dataFormat=POJO")
                .process(exchange -> {
                    Object response = exchange.getIn().getBody();
                    log.info("MESSAGE CONTENT RESPONSE : {}", response);
                    log.info(" Raw OBject response class: {}", response != null ? response.getClass().getName() : "null");

                    org.apache.cxf.message.MessageContentsList list = (org.apache.cxf.message.MessageContentsList) response;

                    TransactionHistoryResponse transactionHistoryResponse = (TransactionHistoryResponse) list.get(0);
                    exchange.setProperty("transactions", transactionHistoryResponse.getTransactions());
                });




        from("cxf:/TransactionService?serviceClass=com.banking.camel.service.TransactionService"
                + "&address=http://0.0.0.0:8083/transactions"
                + "&dataFormat=POJO")
                .bean(transactionServiceImpl, "getTransactionHistory");




    }
}
