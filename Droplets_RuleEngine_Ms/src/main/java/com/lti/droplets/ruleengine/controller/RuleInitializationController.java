package com.lti.droplets.ruleengine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lti.droplets.corelib.exception.DropletsException;
import com.lti.droplets.corelib.model.DropletsRequestModel;
import com.lti.droplets.corelib.model.DropletsResponseModel;
import com.lti.droplets.ruleengine.service.CheckTransactionEligibilityForRuleService;

@RestController
@RequestMapping("/api/v1/rulesInitialization")
public class RuleInitializationController {

    @Autowired
    private CheckTransactionEligibilityForRuleService checkTransactionEligibilityForRuleService;

    @PostMapping("/checkTransactionEligibilityForRule")
    public DropletsResponseModel checkTransactionEligibilityForRule(@RequestBody DropletsRequestModel dropletsRequestModel) throws DropletsException {
        return checkTransactionEligibilityForRuleService.checkTransactionEligibilityForRule(dropletsRequestModel);
    }

}
