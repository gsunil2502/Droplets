package com.lti.droplets.goals.config;

import lombok.Data;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.modelmapper.ModelMapper;

@Configuration
@Data
public class GoalsConfig {

	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}

}
