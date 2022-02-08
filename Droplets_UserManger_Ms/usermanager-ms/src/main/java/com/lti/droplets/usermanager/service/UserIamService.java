package com.lti.droplets.usermanager.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lti.droplets.corelib.exception.DropletsException;
import com.lti.droplets.corelib.model.DropletsRequestModel;
import com.lti.droplets.corelib.model.DropletsResponseModel;
import com.lti.droplets.corelib.model.Status;
import com.lti.droplets.usermanager.entity.UserIam;
import com.lti.droplets.usermanager.repo.UserIamRepo;

@Service
public class UserIamService {

    @Autowired
    private UserIamRepo userIamRepo;



    public DropletsResponseModel login(DropletsRequestModel dropletsRequestModel) throws  DropletsException{
        DropletsResponseModel dropletsResponseModel = new DropletsResponseModel();
        try{
        UserIam userIam = new ObjectMapper().convertValue(dropletsRequestModel.getRequest(), UserIam.class);
        	
        	UserIam userIamDb;
        	
        	if(userIam.getUserMobileNumber()!=null) {
        		// find by mobile number
        		userIamDb =userIamRepo.findByUserMobileNumber(userIam.getUserMobileNumber());
        		if(userIamDb==null) {
        			throw new DropletsException(Status.SUCCESS, "Mobile number not registered");
        		}
        	}
        	else if(userIam.getUserEmail()!=null) {
        		//find by email
        		userIamDb =userIamRepo.findByUserEmail(userIam.getUserEmail());
        		if(userIamDb==null) {
        			throw new DropletsException(Status.SUCCESS, " email not registered");
        		}
        	}
        	
        	else {
        		throw new DropletsException(Status.FAILURE, "Please provide email or mobile number for login");
        	}
        	
           
           
            			Base64 base64 = new Base64();
                        String decodedPazzword = new String(base64.decode(userIamDb.getPazzword().getBytes()));
                        if (decodedPazzword.equals(userIam.getPazzword())) {
                            dropletsResponseModel.setStatus(Status.SUCCESS);
                            dropletsResponseModel.setMessage("Successfully Logged In");
                        } else {
                            dropletsResponseModel.setStatus(Status.FAILURE);
                            dropletsResponseModel.setMessage("Invalid Password");
                        }
        }                
               
           
        catch (Exception e)
        {
            throw new DropletsException(Status.FAILURE,e.getMessage());
        }
        return dropletsResponseModel;
    }



    public DropletsResponseModel setPassword(DropletsRequestModel dropletsRequestModel) throws DropletsException
    {
        DropletsResponseModel dropletsResponseModel = new DropletsResponseModel();
        try{
            UserIam userIam = new ObjectMapper().convertValue(dropletsRequestModel.getRequest(), UserIam.class);
            Base64 base64 = new Base64();
            String encodedPassword = new String(base64.encode(userIam.getPazzword().getBytes()));
                Optional<UserIam> userIamOptional= userIamRepo.findById(userIam.getCustomerId());
            try {
                UserIam userIam1 =userIamOptional.get();
                userIam1.setCustomerId(userIam.getCustomerId());
                userIam1.setLastLoginTime(LocalDateTime.now());
                userIam1.setPazzword(encodedPassword);
                
                userIam1 = userIamRepo.save(userIam1);
                dropletsResponseModel.setStatus(Status.SUCCESS);
                dropletsResponseModel.setMessage("User password updated successfully");
            }
            catch (Exception e)
            {
                throw new DropletsException(Status.FAILURE,"User Not Found");
            }
        }
        catch (Exception e)
        {
            throw new DropletsException(Status.FAILURE,e.getMessage());
        }
        return dropletsResponseModel;
    }



}
