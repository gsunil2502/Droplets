package com.lti.droplets.notifications.service;

import com.lti.droplets.corelib.exception.DropletsException;
import com.lti.droplets.corelib.model.DropletsRequestModel;
import com.lti.droplets.corelib.model.DropletsResponseModel;
import org.springframework.stereotype.Service;

@Service
public class MailNotificationService {

    public DropletsResponseModel sendMailNotification(DropletsRequestModel dropletsRequestModel) throws DropletsException {

        //dummy

        return new DropletsResponseModel();
    }
}
