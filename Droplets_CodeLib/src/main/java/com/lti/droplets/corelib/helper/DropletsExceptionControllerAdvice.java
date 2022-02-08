package com.lti.droplets.corelib.helper;

import com.lti.droplets.corelib.exception.DropletsException;
import com.lti.droplets.corelib.model.DropletsResponseModel;
import com.lti.droplets.corelib.model.Status;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class DropletsExceptionControllerAdvice {

    @ExceptionHandler(DropletsException.class)
    public @ResponseBody DropletsResponseModel handleException(final DropletsException exception) {
        DropletsResponseModel dropletsResponseModel = new DropletsResponseModel();
        dropletsResponseModel.setStatus(Status.FAILURE);
        dropletsResponseModel.setMessage(exception.getMessage());
        return dropletsResponseModel;
    }
}
