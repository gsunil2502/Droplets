package com.lti.droplets.usermanager.service;

import com.lti.droplets.corelib.exception.DropletsException;
import com.lti.droplets.corelib.model.DropletsResponseModel;
import com.lti.droplets.corelib.model.Status;
import com.lti.droplets.usermanager.entity.UserActivationToken;
import com.lti.droplets.usermanager.entity.UserModel;
import com.lti.droplets.usermanager.repo.UserActivationTokenRepo;
import com.lti.droplets.usermanager.repo.UserModelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserActivationByTokenService {
    @Autowired
    private UserActivationTokenRepo userActivationTokenRepo;

    @Autowired
    private UserModelRepo userModelRepo;

    public String getToken(Long Id){

        String token = UUID.randomUUID().toString();
        token = token+Id;

        UserActivationToken userActivationToken = new UserActivationToken();

        userActivationToken.setToken(token);
        userActivationToken.setTokenCreationTime(LocalDateTime.now());
        userActivationToken.setTokenExpiryTime(LocalDateTime.now().plusMinutes(5));
        userActivationToken.setCustomerId(Id);
        userActivationTokenRepo.save(userActivationToken);

        return token;
    }

    public DropletsResponseModel confirmToken(String token) throws DropletsException{
        try{
            Optional<UserActivationToken> userActivationToken = userActivationTokenRepo.findById(token);
            UserActivationToken userActivationToken1=userActivationToken.get();
            LocalDateTime expiredAt = userActivationToken1.getTokenExpiryTime();

            if (expiredAt.isBefore(LocalDateTime.now())) {
                throw new DropletsException(Status.FAILURE, "Activation Link Expired");
            }
            Optional<UserModel> userModelOptional = userModelRepo.findById(userActivationToken1.getCustomerId());
            UserModel userModel = userModelOptional.get();
            userModel.setIsActivated(true);
            userModelRepo.save(userModel);
            DropletsResponseModel dropletsResponseModel =new DropletsResponseModel();
            dropletsResponseModel.setMessage("Account Activated Successfully");
            dropletsResponseModel.setStatus(Status.SUCCESS);

            return dropletsResponseModel;
        }
        catch (Exception e){
            throw  new DropletsException(Status.FAILURE,"Invalid Activation Link");
        }

    }
}
