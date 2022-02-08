package com.lti.droplets.controller;

import com.lti.droplets.corelib.exception.DropletsException;
import com.lti.droplets.corelib.model.DropletsRequestModel;
import com.lti.droplets.corelib.model.DropletsResponseModel;
import com.lti.droplets.service.transactionservice.NewTransactionViaSMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/workflow/transaction")
public class TransactionController {
    @Autowired
    private NewTransactionViaSMSService newTransactionViaSMSService;

    @PostMapping("/newTransactionViaSMS")
    public DropletsResponseModel newTransactionViaSMS(@RequestBody DropletsRequestModel dropletsRequestModel)throws DropletsException{
        return newTransactionViaSMSService.newTransactionViaSMS(dropletsRequestModel);

    }
}
