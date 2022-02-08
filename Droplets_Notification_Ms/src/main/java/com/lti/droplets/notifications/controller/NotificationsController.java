package com.lti.droplets.notifications.controller;

import com.lti.droplets.corelib.exception.DropletsException;
import com.lti.droplets.corelib.model.DropletsRequestModel;
import com.lti.droplets.corelib.model.DropletsResponseModel;
import com.lti.droplets.notifications.model.MailNotificationRequestModel;
import com.lti.droplets.notifications.service.MailNotificationService;
import com.lti.droplets.notifications.service.SendNotificationService;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationsController {

    @Autowired
    private MailNotificationService mailNotificationService;

    @Autowired
    private SendNotificationService sendNotificationService;

    @PostMapping("/sendMailNotification")
    public DropletsResponseModel sendMailNotification(@RequestBody MailNotificationRequestModel dropletsRequestModel) throws DropletsException, MessagingException, TemplateException, IOException {sendNotificationService.sendEmail(
            dropletsRequestModel.getSubjectLine(),
            dropletsRequestModel.getUserEmail(),
            (String)dropletsRequestModel.getNotificationContent());

        return null;
    }
}
