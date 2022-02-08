package com.lti.droplets.ruleengine.controller;

import com.lti.droplets.corelib.exception.DropletsException;
import com.lti.droplets.corelib.model.DropletsRequestModel;
import com.lti.droplets.corelib.model.DropletsResponseModel;
import com.lti.droplets.ruleengine.entity.RuleModel;
import com.lti.droplets.ruleengine.service.RuleCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/rules")
public class RuleCrudController{
    @Autowired
    private RuleCrudService ruleCrudService;

    @PostMapping("/createNewRule")
    public DropletsResponseModel createNewRule(@RequestBody DropletsRequestModel dropletsRequestModel) throws DropletsException {
        return ruleCrudService.createNewRule(dropletsRequestModel);
    }

    @GetMapping("/activateRule")
    public DropletsResponseModel activateRule(@RequestParam Long ruleId) throws DropletsException{
        return ruleCrudService.activateRule(ruleId);
    }

    @GetMapping("/deactivateRule")
    public DropletsResponseModel deactivateRule(@RequestParam Long ruleId) throws DropletsException{
        return ruleCrudService.deactivateRule(ruleId);
    }

    @GetMapping("/deleteRule")
    public DropletsResponseModel deleteRule(@RequestParam Long ruleId) throws DropletsException{
        return ruleCrudService.deleteRule(ruleId);
    }

    @GetMapping("/getAllRulesByCustomerId")
    public DropletsResponseModel getAllRulesByCustomerId(@RequestParam Long customerId) throws DropletsException{
        return ruleCrudService.getAllRulesByCustomerId(customerId);
    }

    @GetMapping("/test")
    public RuleModel test(){
        RuleModel ruleModel = new RuleModel();
        ruleModel.setTransactionFromDate(new Date());
        return ruleModel;
    }
}
