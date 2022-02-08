package com.lti.droplets.ruleengine.model;

import lombok.Data;

@Data
public class RuleResponse {
    private Boolean ruleEligibility;
    private Long transactionId;
    private Double savings;
}
