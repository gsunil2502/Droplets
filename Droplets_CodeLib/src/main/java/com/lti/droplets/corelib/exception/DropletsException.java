package com.lti.droplets.corelib.exception;

import com.lti.droplets.corelib.model.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DropletsException extends  Exception{

    private static final long serialVersionUID = 1L;

    private Status status;
    private String message;

    public DropletsException(Status status, String message){

        super(message);
        this.status = status;
        this.message = message;
    }

}
