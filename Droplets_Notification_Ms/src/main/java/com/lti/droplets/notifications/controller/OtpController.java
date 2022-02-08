package com.lti.droplets.notifications.controller;

import com.lti.droplets.corelib.exception.DropletsException;
import com.lti.droplets.corelib.model.DropletsRequestModel;
import com.lti.droplets.corelib.model.DropletsResponseModel;
import com.lti.droplets.notifications.entity.OtpModel;
import com.lti.droplets.notifications.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/notifications/otp")
public class OtpController {

    @Autowired
    private OtpService otpService;

    @PostMapping("/generateOtp")
    public DropletsResponseModel generateOtp(@RequestBody DropletsRequestModel dropletsRequestModel) throws DropletsException {
        return otpService.generateOtp(dropletsRequestModel);
    }

    @PostMapping("/validateOtp")
    public DropletsResponseModel validateOtp(@RequestBody DropletsRequestModel dropletsRequestModel) throws  DropletsException{
        return otpService.validateOtp(dropletsRequestModel);
    }


}
