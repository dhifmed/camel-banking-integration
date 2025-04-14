package com.banking.camel.routes;

import com.banking.camel.model.BalanceRequest;
import com.banking.camel.service.BankingService;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class BankingRoute extends RouteBuilder {

    BankingService bankingService;
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
                .to("direct:getBalance");

        from("direct:getBalance")
                .log("balance request received: ${body}")
                .process(exchange -> {
                    BalanceRequest rest = exchange.getIn().getBody(BalanceRequest.class);
                    exchange.getIn().setBody(rest.accountNumber);
                })
                .to("cxf://http://localhost:8081/banking"
                        + "?serviceClass=com.banking.camel.service.BankingService"
                        + "&defaultOperationName=getBalance"
                        + "&dataFormat=POJO")
                .log("Banking SOAP response: ${body}")
                .process(exchange -> {
                    double balance = exchange.getIn().getBody(Double.class);
                    System.out.println("BAnking Service response --- Balance :" + balance);
                });



    }
}
