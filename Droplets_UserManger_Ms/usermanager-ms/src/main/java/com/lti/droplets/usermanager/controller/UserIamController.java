package com.lti.droplets.usermanager.controller;

import com.lti.droplets.corelib.exception.DropletsException;
import com.lti.droplets.corelib.model.DropletsRequestModel;
import com.lti.droplets.corelib.model.DropletsResponseModel;
import com.lti.droplets.usermanager.service.UserIamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/userManager/iam")
public class UserIamController {
    @Autowired
    private UserIamService userIamService;

    @PostMapping("/setPassword")
    public DropletsResponseModel setPassword(@RequestBody DropletsRequestModel dropletsRequestModel) throws DropletsException
    {
        return userIamService.setPassword(dropletsRequestModel);
    }

    @PostMapping("/login")
    public DropletsResponseModel login(@RequestBody DropletsRequestModel dropletsRequestModel) throws DropletsException
    {
        return userIamService.login(dropletsRequestModel);
    }
}
