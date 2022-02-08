package com.lti.droplets.corelib.model;

import lombok.Data;

@Data
public class DropletsResponseModel {

    private Object result;
    private Status status;
    private String message;


}
