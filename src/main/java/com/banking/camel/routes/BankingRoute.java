package com.banking.camel.routes;

import com.banking.camel.service.BankingService;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class BankingRoute extends RouteBuilder {

    BankingService bankingService;
    @Override
    public void configure() throws Exception {
        System.out.println("CAMEL RUNNING -------");

        from("timer:callSoap?period=10000")
                .setBody(constant("ACC789"))
                .log("Calling Banking SOAP service with body: ${body}")
                .to("cxf://http://localhost:8081/banking"
                        + "?serviceClass=com.banking.camel.service.BankingService"
                        + "&defaultOperationName=getBalance"
                        + "&dataFormat=POJO")
                .log("Banking SOAP response: ${body}")
                .process(exchange -> {
                    Double result = exchange.getIn().getBody(Double.class);
                    System.out.println("Banking service result: " + result);
                });



    }
}
