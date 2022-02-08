package com.lti.droplets.usermanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lti.droplets.corelib.exception.DropletsException;
import com.lti.droplets.corelib.model.DropletsRequestModel;
import com.lti.droplets.corelib.model.DropletsResponseModel;
import com.lti.droplets.corelib.model.Status;
import com.lti.droplets.usermanager.entity.DropletsModel;
import com.lti.droplets.usermanager.entity.UserModel;
import com.lti.droplets.usermanager.repo.DropletsModelRepo;
import com.lti.droplets.usermanager.repo.UserModelRepo;

@Service
public class NewDropletsOnboardingService {

	@Autowired
	private UserModelRepo userModelRepo;
	
	@Autowired
	private DropletsModelRepo dropletsModelRepo;
	
	public DropletsResponseModel newDropletsOnboarding(DropletsRequestModel dropletsRequestModel) throws DropletsException{

		try {
			DropletsModel dropletsModel = new ObjectMapper().convertValue(dropletsRequestModel.getRequest(), DropletsModel.class);
			UserModel userModel= null;
			try {
			 userModel=  userModelRepo.findById(dropletsModel.getCustomerId()).get();
			}
			catch(Exception e ) {
				throw new DropletsException(Status.FAILURE, "User not found");
			}

			if(userModel.getIsActivated()) {
				 dropletsModelRepo.save(dropletsModel);

				 DropletsResponseModel dropletsResponseModel = new DropletsResponseModel();
				 dropletsResponseModel.setMessage("Droplets Onbording successfully");
				 dropletsResponseModel.setResult(dropletsModel);
				 dropletsResponseModel.setStatus(Status.SUCCESS);

				 return dropletsResponseModel;
			}


			else {
				throw new DropletsException(Status.FAILURE, "User Not Activated");
			}
		}
		catch (Exception e)
		{
			throw new DropletsException(Status.FAILURE, e.getMessage());
		}
		}
	}