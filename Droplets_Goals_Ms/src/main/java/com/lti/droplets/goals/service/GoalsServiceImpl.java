package com.lti.droplets.goals.service;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lti.droplets.corelib.exception.DropletsException;
import com.lti.droplets.corelib.model.DropletsRequestModel;
import com.lti.droplets.corelib.model.DropletsResponseModel;
import com.lti.droplets.corelib.model.Status;
import com.lti.droplets.goals.model.GoalsModel;
import com.lti.droplets.goals.model.UpdateGoalRequestModel;
import com.lti.droplets.goals.repository.GoalsRepository;

import com.lti.droplets.goals.repository.UpdateGoalRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GoalsServiceImpl {

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private GoalsRepository goalsRepository;
	@Autowired
	private UpdateGoalRepository updateGoalRepository;

	public DropletsResponseModel getAllGoals(Long customerId) throws DropletsException {
		DropletsResponseModel dropletsResponseModel = new DropletsResponseModel();
		try {
			List<GoalsModel> goals = goalsRepository.findAllByCustomerId(customerId);
			dropletsResponseModel.setResult(goals);
			dropletsResponseModel.setStatus(Status.SUCCESS);
			dropletsResponseModel.setMessage("Goals Fetched Successfully");
		} catch (Exception e) {
			e.printStackTrace();
			throw new DropletsException(Status.FAILURE, e.getMessage());
		}
		return dropletsResponseModel;
	}
	
	public DropletsResponseModel activateGoal(Long goalId) throws  DropletsException{
        DropletsResponseModel dropletsResponseModel = new DropletsResponseModel();
        try{
            Optional<GoalsModel> goalModelOptional= goalsRepository.findById(goalId);
            try{
            	GoalsModel goals = goalModelOptional.get();
                if(goals.getIsActivated()){
                    throw new DropletsException(Status.FAILURE,"Goal already Activated");
                }
                else{
                	goals.setIsActivated(true);
    				goals.setDropletStartDate(new java.sql.Date(System.currentTimeMillis()));
    				goals.setDropletEndDate(null);
    				goalsRepository.save(goals);
                    dropletsResponseModel.setResult(goals);
                    dropletsResponseModel.setStatus(Status.SUCCESS);
                    dropletsResponseModel.setMessage("Goal Activated Successfully");
                    return dropletsResponseModel;
                }

            }catch (Exception e){
                throw new DropletsException(Status.FAILURE,e.getMessage());
            }
        }
        catch (Exception e){
            throw new DropletsException(Status.FAILURE,e.getMessage());
        }

    }


	@Transactional
	public DropletsResponseModel deactivateGoal(Long goalId) throws DropletsException {
		DropletsResponseModel dropletsResponseModel = new DropletsResponseModel();
		GoalsModel goals = null;
		try {
			goals = goalsRepository.findByGoalId(goalId);
			if (!goals.getIsActivated()) {
				throw new DropletsException(Status.FAILURE, "Goal already Deactivated");
			} else {
				goals.setIsActivated(false);
				goals.setDropletEndDate(new java.sql.Date(System.currentTimeMillis()));
				goalsRepository.save(goals);
				dropletsResponseModel.setStatus(Status.SUCCESS);
				dropletsResponseModel.setMessage("Goal Deactivated Successfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DropletsException(Status.FAILURE, e.getMessage());
		}
		return dropletsResponseModel;
	}

	@Transactional
	public DropletsResponseModel addGoal(DropletsRequestModel dropletsRequestModel) throws DropletsException {
		GoalsModel goalsModel = null;
		GoalsModel requestModel = null;
		DropletsResponseModel dropletsResponseModel = new DropletsResponseModel();
		try {
			goalsModel = new ObjectMapper().convertValue(dropletsRequestModel.getRequest(), GoalsModel.class);
			System.out.println(goalsModel);
			requestModel = goalsModel;
			requestModel.setDropletStartDate(new java.sql.Date(System.currentTimeMillis()));
			if (goalsModel.getGoalId() != null) {
				goalsModel = goalsRepository.findByGoalId(goalsModel.getGoalId());
				goalsModel = modelMapper.map(requestModel, GoalsModel.class);
				goalsModel.setIsActivated(false);
				goalsModel.setTotalSavingsPercentage(0.0);
				goalsModel.setTotalSavingsAmount(0.0);
				goalsRepository.save(goalsModel);
			} else {
				goalsModel.setIsActivated(false);
				goalsModel.setTotalSavingsPercentage(0.0);
				goalsModel.setTotalSavingsAmount(0.0);
				goalsRepository.save(goalsModel);
			}
			dropletsResponseModel.setResult(goalsModel);
			dropletsResponseModel.setStatus(Status.SUCCESS);
			dropletsResponseModel.setMessage("Goal Created Successfully");
		} catch (Exception e) {
			e.printStackTrace();
			throw new DropletsException(Status.FAILURE, e.getMessage());
		}

		return dropletsResponseModel;
	}

	@Transactional
	public DropletsResponseModel updateGoal(DropletsRequestModel dropletsRequestModel) throws DropletsException{
		try
		{
			DropletsResponseModel dropletsResponseModel = new DropletsResponseModel();
			UpdateGoalRequestModel updateGoalRequestModel = new ObjectMapper().convertValue(dropletsRequestModel.getRequest(), UpdateGoalRequestModel.class);
			try
			{
				GoalsModel goalsModel = goalsRepository.findByGoalId(updateGoalRequestModel.getGoalId());
				Double totalSavingsAmount = goalsModel.getTotalSavingsAmount();
				Double totalSavingsPercentage = goalsModel.getTotalSavingsPercentage();
				Double maximumSavingsAmount = goalsModel.getMaximumSavingsAmount();
				Double savingAmountOnTransaction = updateGoalRequestModel.getSavingAmount();
				Double savingPercentageOnTransaction = (savingAmountOnTransaction/maximumSavingsAmount)*100;
				totalSavingsPercentage += savingPercentageOnTransaction;
				totalSavingsAmount += savingAmountOnTransaction;
				goalsModel.setTotalSavingsAmount(totalSavingsAmount);
				goalsModel.setTotalSavingsPercentage(totalSavingsPercentage);
				goalsModel = goalsRepository.save(goalsModel);
				//updateGoalRequestModel.setIsUpdated(true);
				updateGoalRequestModel.setSavingAmount(savingAmountOnTransaction);
				updateGoalRequestModel.setSavingPercentage(savingPercentageOnTransaction);
				updateGoalRequestModel = updateGoalRepository.save(updateGoalRequestModel);
				dropletsResponseModel.setResult(goalsModel);
				dropletsResponseModel.setStatus(Status.SUCCESS);
				dropletsResponseModel.setMessage("Updated successfully");
				return dropletsResponseModel;
			}
			catch (Exception e)
			{
				throw new DropletsException(Status.FAILURE,"No goals founded");
			}
		}
		catch (Exception e)
		{
			throw new DropletsException(Status.FAILURE,e.getMessage());
		}
	}


	@Transactional
	public DropletsResponseModel deleteGoal(Long goalId) throws DropletsException {
		DropletsResponseModel dropletsResponseModel = new DropletsResponseModel();
		GoalsModel goals = null;
		try {
			goals = goalsRepository.findByGoalId(goalId);
			goalsRepository.delete(goals);
			dropletsResponseModel.setMessage("Goal Deleted Successfully");
			dropletsResponseModel.setStatus(Status.SUCCESS);
			return dropletsResponseModel;
		} catch (Exception e) {
			throw new DropletsException(Status.FAILURE, e.getMessage());
		}

	}

}
