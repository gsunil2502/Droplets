package com.lti.droplets.router;

import com.lti.droplets.corelib.model.DropletsResponseModel;
import com.lti.droplets.corelib.model.Status;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
public class RestInvoker {

    RestTemplate restTemplate = new RestTemplate();
    public ResponseEntity invoke(String url, Object requestEntity)throws Exception{

            URI uri = new URI(url);

            HttpEntity<Object> request = new HttpEntity(requestEntity);

           return  restTemplate.postForEntity(uri, request, DropletsResponseModel.class);



    }
}
