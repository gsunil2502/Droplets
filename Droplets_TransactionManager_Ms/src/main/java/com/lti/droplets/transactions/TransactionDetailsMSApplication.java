package com.lti.droplets.transactions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.lti.droplets"})
public class TransactionDetailsMSApplication extends SpringBootServletInitializer {

	public static void main(String[] args)
	{
		SpringApplication.run(TransactionDetailsMSApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
	{
		return application.sources(TransactionDetailsMSApplication.class);
	}


}
