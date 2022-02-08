package com.lti.droplets.ruleengine.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lti.droplets.corelib.exception.DropletsException;
import com.lti.droplets.corelib.model.DropletsRequestModel;
import com.lti.droplets.corelib.model.DropletsResponseModel;
import com.lti.droplets.corelib.model.Status;
import com.lti.droplets.ruleengine.entity.RuleModel;
import com.lti.droplets.ruleengine.repo.RuleModelRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RuleCrudService {

    @Autowired
    private RuleModelRepo ruleModelRepo;

    public DropletsResponseModel createNewRule(DropletsRequestModel dropletsRequestModel)throws DropletsException {
        try{
            RuleModel ruleModel = new ObjectMapper().convertValue(dropletsRequestModel.getRequest(), RuleModel.class);
            ruleModel.setIsActivated(false);
            ruleModel =  ruleModelRepo.save(ruleModel);

            DropletsResponseModel dropletsResponseModel = new DropletsResponseModel();
            dropletsResponseModel.setResult(ruleModel);
            dropletsResponseModel.setStatus(Status.SUCCESS);
            dropletsResponseModel.setMessage("Rule Created Successfully");
            return dropletsResponseModel;

        }
        catch (Exception e){
            throw new DropletsException(Status.FAILURE,e.getMessage());
        }
    }

    public DropletsResponseModel activateRule(Long ruleId) throws  DropletsException{

        DropletsResponseModel dropletsResponseModel = new DropletsResponseModel();
        try{

            Optional<RuleModel> ruleModelOptional= ruleModelRepo.findById(ruleId);

            try{
                RuleModel ruleModel = ruleModelOptional.get();
                if(ruleModel.getIsActivated()){
                    throw new DropletsException(Status.FAILURE,"Rule already Activated");
                }
                else{
                    ruleModel.setIsActivated(true);
                    ruleModel=ruleModelRepo.save(ruleModel);
                    dropletsResponseModel.setResult(ruleModel);
                    dropletsResponseModel.setStatus(Status.SUCCESS);
                    dropletsResponseModel.setMessage("Rule Activated Successfully");
                    return dropletsResponseModel;
                }

            }catch (Exception e){
                throw new DropletsException(Status.FAILURE,"Rule Not Found");
            }
        }
        catch (Exception e){
            throw new DropletsException(Status.FAILURE,e.getMessage());
        }

    }

    public DropletsResponseModel deactivateRule(Long ruleId) throws  DropletsException{

        DropletsResponseModel dropletsResponseModel = new DropletsResponseModel();
        try{

            Optional<RuleModel> ruleModelOptional= ruleModelRepo.findById(ruleId);

            try{
                RuleModel ruleModel = ruleModelOptional.get();
                if(!ruleModel.getIsActivated()){
                    throw new DropletsException(Status.FAILURE,"Rule already Deactivated");
                }
                else{
                    ruleModel.setIsActivated(false);
                    dropletsResponseModel.setResult(ruleModel);
                    dropletsResponseModel.setStatus(Status.SUCCESS);
                    dropletsResponseModel.setMessage("Rule Deactivated Successfully");
                    return dropletsResponseModel;
                }

            }catch (Exception e){
                throw new DropletsException(Status.FAILURE,"Rule Not Found");
            }
        }
        catch (Exception e){
            throw new DropletsException(Status.FAILURE,e.getMessage());
        }

    }

    public DropletsResponseModel deleteRule(Long ruleId) throws  DropletsException{

        DropletsResponseModel dropletsResponseModel = new DropletsResponseModel();
        try{

          ruleModelRepo.deleteById(ruleId);
          dropletsResponseModel.setMessage("Rule Deleted Successfully");
          dropletsResponseModel.setStatus(Status.SUCCESS);
          return dropletsResponseModel;
        }
        catch (Exception e){
            throw new DropletsException(Status.FAILURE,e.getMessage());
        }

    }

    public DropletsResponseModel getAllRulesByCustomerId(Long customerId) throws  DropletsException{

        DropletsResponseModel dropletsResponseModel = new DropletsResponseModel();
        try{

            List<RuleModel> rules= ruleModelRepo.findAllByCustomerId(customerId);
            dropletsResponseModel.setMessage("Rules Fetched Successfully");
            dropletsResponseModel.setStatus(Status.SUCCESS);
            dropletsResponseModel.setResult(rules);
            return dropletsResponseModel;
        }
        catch (Exception e){
            throw new DropletsException(Status.FAILURE,e.getMessage());
        }

    }
}
