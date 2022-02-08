package com.lti.droplets.usermanager.model;

import lombok.Data;



@Data
public class MailNotificationRequestModel {

    private String notificationContent;

    private String userEmail;

    private String subjectLine;
    private String operation;


}
