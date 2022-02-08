package com.lti.droplets.notifications.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lti.droplets.corelib.exception.DropletsException;
import com.lti.droplets.corelib.model.DropletsRequestModel;
import com.lti.droplets.corelib.model.DropletsResponseModel;
import com.lti.droplets.corelib.model.Status;
import com.lti.droplets.notifications.config.OtpConfig;
import com.lti.droplets.notifications.entity.OtpModel;
import com.lti.droplets.notifications.helper.MailContentCreatorHelper;
import com.lti.droplets.notifications.repo.OtpModelRepo;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.Random;

@Service
public class OtpService {

    @Autowired
    private OtpConfig otpConfig;

    @Autowired
    private OtpModelRepo otpModelRepo;

    @Autowired
    private MailContentCreatorHelper mailContentCreatorHelper;

    @Autowired
    private SendNotificationService sendNotificationService;

    @Autowired
    @Qualifier("getMaxOtpValue")
    private int maxOtpSize;

    public DropletsResponseModel generateOtp(DropletsRequestModel dropletsRequestModel) throws DropletsException {

        try {

            OtpModel otpModel = new ObjectMapper().convertValue(dropletsRequestModel.getRequest(), OtpModel.class);
            String otp = String.format("%0" + otpConfig.getOtpSize() + "d", new Random().nextInt(maxOtpSize));
            Base64 base64 = new Base64();
            String encodedOtp = new String(base64.encode(otp.getBytes()));

            DropletsResponseModel dropletsResponseModel = new DropletsResponseModel();
            otpModel.setOtpCreationDate(new Date(System.currentTimeMillis()));
            otpModel.setOtpExpiryDate(new Date(System.currentTimeMillis() + Integer.parseInt(otpConfig.getExpiryTime())));
            otpModel.setValidated(false);
            otpModel.setOtpValue(encodedOtp);
            HashMap<String, Object> model = new HashMap<String, Object>();
            model.put("otp", otp);

            String mailContent = mailContentCreatorHelper.getEmailContent(model, "otp-eail.ftlh");
            sendNotificationService.sendEmail("Droplets OTP", otpModel.getUserEmail(), mailContent);



            OtpModel result = otpModelRepo.save(otpModel);
            result.setOtpValue(null);
            dropletsResponseModel.setResult(result);
            dropletsResponseModel.setMessage("OTP generated and sent successfully");
            dropletsResponseModel.setStatus(Status.SUCCESS);
            return dropletsResponseModel;


        }
        catch (Exception e){
            throw new DropletsException(Status.FAILURE, e.getMessage());
        }

    }


    public DropletsResponseModel validateOtp(DropletsRequestModel dropletsRequestModel) throws DropletsException {
        try {

            OtpModel otpModel = new ObjectMapper().convertValue(dropletsRequestModel.getRequest(), OtpModel.class);
            Optional<OtpModel> otpModelOptaional = otpModelRepo.findById(otpModel.getOtpId());
            OtpModel otpModelResult = otpModelOptaional.get();
            Date currentTime = new Date(System.currentTimeMillis());
            int dat = currentTime.compareTo(otpModelResult.getOtpExpiryDate());
            DropletsResponseModel dropletsResponseModel = new DropletsResponseModel();
            if (dat == -1) {
                Base64 base64 = new Base64();
                String decodedOtp = new String(base64.decode(otpModelResult.getOtpValue().getBytes()));


                if (otpModel.getOtpValue().equalsIgnoreCase(decodedOtp)) {
                    otpModelResult.setValidated(true);
                    dropletsResponseModel.setMessage("OTP Verified Successfully.");

                    dropletsResponseModel.setStatus(Status.SUCCESS);


                    return dropletsResponseModel;
                } else {
                    dropletsResponseModel.setMessage("Invalid Otp Entered");
                    dropletsResponseModel.setStatus(Status.FAILURE);
                    return dropletsResponseModel;
                }
            } else {
                dropletsResponseModel.setMessage("OTP expired");
                dropletsResponseModel.setStatus(Status.FAILURE);
                return dropletsResponseModel;
            }


        }
     catch (Exception e){
            throw new DropletsException(Status.FAILURE, e.getMessage());

    }



    }

}
