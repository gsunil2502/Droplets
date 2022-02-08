package com.lti.droplets.goals;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.lti.droplets"})
public class DropletsGoalsApplication extends SpringBootServletInitializer {

	public static void main(String[] args)
	{
		SpringApplication.run(DropletsGoalsApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
	{
		return application.sources(DropletsGoalsApplication.class);
	}
	
}
