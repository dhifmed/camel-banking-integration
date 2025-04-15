package com.banking.camel;

import com.banking.camel.service.TransactionServiceImpl;
import jakarta.xml.ws.Endpoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CamelApplication {


	public static void main(String[] args) {
		System.out.println("Running Camel Application ....");
		SpringApplication.run(CamelApplication.class, args);

		/*
		System.setProperty("jakarta.xml.ws.spi.Provider",
				"org.apache.cxf.jaxws.spi.ProviderImpl");
		Endpoint.publish("http://localhost:8083/transactions", new TransactionServiceImpl());
		System.out.println("Transaction service started ... ");

		 */
	}

}
