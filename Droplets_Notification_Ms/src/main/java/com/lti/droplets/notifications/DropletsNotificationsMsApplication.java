package com.lti.droplets.notifications;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.lti.droplets.corelib","com.lti.droplets.notifications"})
public class DropletsNotificationsMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DropletsNotificationsMsApplication.class, args);
	}

}
