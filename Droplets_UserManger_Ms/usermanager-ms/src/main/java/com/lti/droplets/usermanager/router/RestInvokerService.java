package com.lti.droplets.usermanager.router;

import com.lti.droplets.usermanager.model.MailNotificationRequestModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
public class RestInvokerService {

    public String invoke(String notificationContent, String userMailId) {
        RestTemplate restTemplate = new RestTemplate();

        try {
            final String baseUrl = "http://localhost:8084/api/v1/notifications/sendMailNotification";
            URI uri = new URI(baseUrl);
            MailNotificationRequestModel mailNotificationRequestModel = new MailNotificationRequestModel();
            mailNotificationRequestModel.setNotificationContent(notificationContent);
            mailNotificationRequestModel.setSubjectLine("Welcome | Droplet Onboarding");
            mailNotificationRequestModel.setUserEmail(userMailId);

            //HttpHeaders headers = new HttpHeaders();
            //headers.set("X-COM-PERSIST", "true");
            //headers.set("X-COM-LOCATION", "USA");

            HttpEntity<MailNotificationRequestModel> request = new HttpEntity(mailNotificationRequestModel);

            ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
