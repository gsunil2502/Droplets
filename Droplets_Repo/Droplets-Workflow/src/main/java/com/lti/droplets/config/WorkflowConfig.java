package com.lti.droplets.config;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class WorkflowConfig {

    private String rulesMsIpAddress = "http://localhost:8085/api/v1";
    private String transactionManagerMsIpAddress = "http://localhost:8083/api/v1";
	private String transactionMsUrlAddress= "http://localhost:8083/api/v1/transactionDetails/saveTransactionsInitiateRule";
}
