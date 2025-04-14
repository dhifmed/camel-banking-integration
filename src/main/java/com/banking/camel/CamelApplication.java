package com.banking.camel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
//@ComponentScans(scanB= "com.banking.camel")
public class CamelApplication {


	public static void main(String[] args) {
		System.out.println("Running Camel Application ....");
		SpringApplication.run(CamelApplication.class, args);
	}

}
