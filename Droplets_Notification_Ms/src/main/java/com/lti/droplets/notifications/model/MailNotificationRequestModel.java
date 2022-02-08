package com.lti.droplets.notifications.model;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class MailNotificationRequestModel {

    private Object notificationContent;

    @Email(message = "email is invalid")
    private String userEmail;

    private String subjectLine;
    private String operation;


}
