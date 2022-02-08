package com.lti.droplets.usermanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;



@SpringBootApplication
@ComponentScan({"com.lti.droplets"})
public class UsermanagerMsApplication extends SpringBootServletInitializer{

	public static void main(String[] args)
	{
		SpringApplication.run(UsermanagerMsApplication.class, args);
	}

	@Override  
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application)   
	{  
	return application.sources(UsermanagerMsApplication.class);  
	}  
}
