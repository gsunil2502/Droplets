package com.lti.droplets.usermanager.service;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lti.droplets.corelib.exception.DropletsException;
import com.lti.droplets.corelib.model.DropletsRequestModel;
import com.lti.droplets.corelib.model.DropletsResponseModel;
import com.lti.droplets.corelib.model.Status;
import com.lti.droplets.usermanager.entity.UserIam;
import com.lti.droplets.usermanager.entity.UserModel;
import com.lti.droplets.usermanager.helper.EmailHelper;
import com.lti.droplets.usermanager.repo.UserIamRepo;
import com.lti.droplets.usermanager.repo.UserModelRepo;
import com.lti.droplets.usermanager.router.RestInvokerService;

@Service
public class UserManagerService {
	
	@Autowired
	private UserModelRepo userModelRepo;
	@Autowired
	private UserIamRepo userIamRepo;
	@Autowired
	private RestInvokerService restInvokerService;

	@Autowired
	private  UserActivationByTokenService userActivationByTokenService;

	@Transactional
	public DropletsResponseModel newUserRegistration(DropletsRequestModel dropletsRequestModel)throws DropletsException
	{
		DropletsResponseModel dropletsResponseModel = new DropletsResponseModel();
		try {
			UserModel userModel = new ObjectMapper().convertValue(dropletsRequestModel.getRequest(), UserModel.class);
			if (checkUserExistence(userModel)) {
				throw new DropletsException(Status.FAILURE, "Email Id or mobile already exists");
			} else {
				userModel.setIsActivated(false);
				String temp = userModel.getPassword();
				userModel.setPassword(null);
				userModel = userModelRepo.save(userModel);
				UserIam userIam = new UserIam();
				userIam.setCustomerId(userModel.getUserId());
				userIam.setLastLoginTime(LocalDateTime.now());
				userIam.setUserEmail(userModel.getUserEmail());
				userIam.setUserMobileNumber(userModel.getUserMobileNumber());

				Base64 base64 = new Base64();
				String encodedPassword = new String(base64.encode(temp.getBytes()));
				userIam.setPazzword(encodedPassword);
				userIamRepo.save(userIam);


				String token = userActivationByTokenService.getToken(userModel.getUserId());
				String link = "http://localhost:8081/api/v1/userManager/userAccountActivationUsingToken/?token=" + token;
				String emailContent = EmailHelper.buildEmail(userModel.getUserName(), link);
				restInvokerService.invoke(emailContent, userModel.getUserEmail());
				dropletsResponseModel.setResult(userModel);
				dropletsResponseModel.setStatus(Status.SUCCESS);
				dropletsResponseModel.setMessage("Registration successful");
			}
		}
		catch (Exception e)
		{
			throw new DropletsException(Status.FAILURE,e.getMessage());
		}
        return dropletsResponseModel;
	}


	public DropletsResponseModel getUserByCustomerId(Long id) throws DropletsException {
		DropletsResponseModel dropletsResponseModel = new DropletsResponseModel();


		try {
			Optional<UserModel> userModelOptional= userModelRepo.findById(id);
			UserModel userModel =userModelOptional.get();
			dropletsResponseModel.setResult(userModel);
			dropletsResponseModel.setStatus(Status.SUCCESS);
			dropletsResponseModel.setMessage("user retrived successfully");
		}
		catch (Exception e)
		{
			throw new DropletsException(Status.FAILURE,"User Not Found");
		}

		return dropletsResponseModel;
	}
	public DropletsResponseModel modifyUser(DropletsRequestModel dropletsRequestModel) throws DropletsException {
		try {
			DropletsResponseModel dropletsResponseModel = new DropletsResponseModel();
			UserModel userModel = new ObjectMapper().convertValue(dropletsRequestModel.getRequest(), UserModel.class);
			try {

				userModel = userModelRepo.save(userModel);
				dropletsResponseModel.setResult(userModel);
				dropletsResponseModel.setStatus(Status.SUCCESS);
				dropletsResponseModel.setMessage("Updated Successfully");
				return dropletsResponseModel;
			} catch (Exception e) {
				throw new DropletsException(Status.FAILURE, "User does not exist");
			}
		}
		catch (Exception e)
		{
			throw new DropletsException(Status.FAILURE,e.getMessage());
		}
	}

	public DropletsResponseModel activateUser(Long customerId) throws DropletsException {

		DropletsResponseModel dropletsResponseModel = new DropletsResponseModel();
		try{

			Optional<UserModel> userModelOptional= userModelRepo.findById(customerId);
			try{
				UserModel userModel = userModelOptional.get();
				if(userModel.getIsActivated()){
					throw new DropletsException(Status.FAILURE,"User is already Activated");
				}
				else{
					userModel.setIsActivated(true);
					userModel = userModelRepo.save(userModel);
					dropletsResponseModel.setStatus(Status.SUCCESS);
					dropletsResponseModel.setMessage("User is activated Successfully");
					return dropletsResponseModel;
				}

			}catch (Exception e){
				throw new DropletsException(Status.FAILURE,e.getMessage());
			}
		}
		catch (Exception e){
			throw new DropletsException(Status.FAILURE,"User Not Found");
		}

	}

    public Boolean checkUserExistence(UserModel userModel) throws DropletsException {
        Boolean emailBoolean,mobileBoolean;
        try{
            Optional<UserModel> userModelOptionalEmail= Optional.ofNullable(userModelRepo.findByUserEmail(userModel.getUserEmail()));
            userModelOptionalEmail.get();
            emailBoolean=true;
        }
        catch (Exception e){
            emailBoolean=false;
        }
        try {
            Optional<UserModel> userModelOptionalMobile= Optional.ofNullable(userModelRepo.findByUserMobileNumber(userModel.getUserMobileNumber()));
            userModelOptionalMobile.get();
            mobileBoolean=true;
        }
        catch (Exception e)
        {
            mobileBoolean=false;
        }
        if(emailBoolean || mobileBoolean)
            return true;
        return false;
    }

}
