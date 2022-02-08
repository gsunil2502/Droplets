package com.lti.droplets.ruleengine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lti.droplets.corelib.model.DropletsRequestModel;
import com.lti.droplets.corelib.model.DropletsResponseModel;
import com.lti.droplets.corelib.model.Status;
import com.lti.droplets.ruleengine.entity.RuleModel;
import com.lti.droplets.ruleengine.model.RuleResponse;
import com.lti.droplets.ruleengine.model.TransactionsModel;
import com.lti.droplets.ruleengine.repo.RuleModelRepo;

@Service
public class CheckTransactionEligibilityForRuleService {

    @Autowired
    private RuleModelRepo ruleModelRepo;

    public DropletsResponseModel checkTransactionEligibilityForRule (DropletsRequestModel dropletsRequestModel){
        TransactionsModel transactionsModel = new ObjectMapper().convertValue(dropletsRequestModel.getRequest(), TransactionsModel.class);

       // Step 1:: check for rules associated with droplet Id.
        List<RuleModel> ruleModelList =ruleModelRepo.findAllByDropletIdAndIsActivated(transactionsModel.getDropletId(),true);
        DropletsResponseModel dropletsResponseModel = new DropletsResponseModel();
        if(!ruleModelList.isEmpty()){
            Double saving=0.0;
            boolean ruleApplicable =false;
            for(int i =0; i<ruleModelList.size();i++){

                RuleModel ruleModel = ruleModelList.get(i);

                if(ruleModel.getTransactionAmountGreaterThan()<transactionsModel.getOriginalTrxAmount()){
                    saving=ruleModel.getFixedAmount();
                    ruleApplicable=true;
                }

            }

            RuleResponse ruleResponse = new RuleResponse();
            if(ruleApplicable){
                //send for droplet transaction
                dropletsResponseModel.setStatus(Status.SUCCESS);
                dropletsResponseModel.setMessage("Submitted for droplet transaction Successfully. Total Saving: "+saving);
                ruleResponse.setTransactionId(transactionsModel.getTransactionId());
                ruleResponse.setRuleEligibility(true);
                ruleResponse.setSavings(saving);
                dropletsResponseModel.setResult(ruleResponse);

                return dropletsResponseModel;

            }
            else {
                dropletsResponseModel.setMessage("No rules applicable for transaction");
                dropletsResponseModel.setStatus(Status.SUCCESS);
                ruleResponse.setTransactionId(transactionsModel.getTransactionId());
                ruleResponse.setRuleEligibility(false);
                ruleResponse.setSavings(0.00);
                dropletsResponseModel.setResult(ruleResponse);
                return dropletsResponseModel;

            }

        }

        dropletsResponseModel.setMessage("No rules active rules found");
        dropletsResponseModel.setStatus(Status.SUCCESS);
        return dropletsResponseModel;

    }




}
