package com.lti.droplets.goals.controller;

import com.lti.droplets.corelib.model.Status;
import com.lti.droplets.goals.model.GoalsModel;
import com.lti.droplets.goals.model.UpdateGoalRequestModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.lti.droplets.corelib.exception.DropletsException;
import com.lti.droplets.corelib.model.DropletsRequestModel;
import com.lti.droplets.corelib.model.DropletsResponseModel;
import com.lti.droplets.goals.service.GoalsServiceImpl;

import java.util.Date;

/**
 * @author Sagi Sunitha{Sunitha.Sagi@lntinfotech.com}
 * 
 */

@RestController
@RequestMapping("/api/v1/goals")
public class GoalsController {

	Logger logger = LoggerFactory.getLogger(GoalsController.class);

	@Autowired
	private GoalsServiceImpl goalsService;

	/**
	 * Method to get all the active goals of the customer based on customer id
	 * 
	 * @param customerId {@code String}
	 * @return DropletsResponseModel which contains the result
	 * @throws DropletsException when there is an error while fetching details
	 */
	@GetMapping("/getAllGoals")
	public DropletsResponseModel getAllActiveGoals(@RequestParam Long customerId) throws DropletsException {
		logger.info("In get all  goals method. ");
		return goalsService.getAllGoals(customerId);
	}
	

	@GetMapping("/activateGoal")
    public DropletsResponseModel activateGoal(@RequestParam Long goalId) throws DropletsException{
        return goalsService.activateGoal(goalId);
    }

	/**
	 * Method to deactivate the goal of the customer based on goal id
	 * 
	 * @param goalId {@code Long}
	 * @return DropletsResponseModel
	 * @throws DropletsException when there is an error while deactivating the goal
	 */
	@GetMapping("/deactivateGoal")
	public DropletsResponseModel deactivateGoal(@RequestParam Long goalId) throws DropletsException {
		logger.info("In deactivate goal method. ");
		return goalsService.deactivateGoal(goalId);
	}

	/**
	 * Method to add new goal or modify existing goal of the customer
	 * 
	 * @param dropletsRequestModel {@code DropletsRequestModel} which consists of
	 *                             goal details
	 * @return DropletsResponseModel which contains the result
	 * @throws DropletsException when there is an error while modifying or adding
	 *                           details
	 */
	@PostMapping("/addGoal")
	public DropletsResponseModel addGoal(@RequestBody DropletsRequestModel dropletsRequestModel)
			throws DropletsException {
		logger.info("In add or update goals method. ");
		return goalsService.addGoal(dropletsRequestModel);

	}
	@PutMapping("/updateGoal")
	public DropletsResponseModel updateGoal(@RequestBody DropletsRequestModel dropletsRequestModel) throws  DropletsException{
		return goalsService.updateGoal(dropletsRequestModel);
	}

	/**
	 * Method to delete the goal of the customer based on goal id
	 * 
	 * @param goalId {@code Long}
	 * @return DropletsResponseModel
	 * @throws DropletsException when there is an error while deactivating the goal
	 */
	@GetMapping("/deleteGoal")
	public DropletsResponseModel deleteRule(@RequestParam Long goalId) throws DropletsException {
		logger.info("In delete goals method. ");
		return goalsService.deleteGoal(goalId);
	}

	@GetMapping("/test")
	public DropletsResponseModel testing() throws DropletsException {
		GoalsModel goalsModel = new GoalsModel();
		DropletsResponseModel dropletsResponseModel=new DropletsResponseModel();
		dropletsResponseModel.setMessage("successful get");
		dropletsResponseModel.setStatus(Status.SUCCESS);
		dropletsResponseModel.setResult(goalsModel);
		return dropletsResponseModel;
	}
	@GetMapping("/testUpdate")
	public DropletsResponseModel testupdate() throws DropletsException {
		UpdateGoalRequestModel updateGoalRequestModel = new UpdateGoalRequestModel();
		DropletsResponseModel dropletsResponseModel=new DropletsResponseModel();
		dropletsResponseModel.setMessage("successful get");
		dropletsResponseModel.setStatus(Status.SUCCESS);
		dropletsResponseModel.setResult(updateGoalRequestModel);
		return dropletsResponseModel;
	}
}