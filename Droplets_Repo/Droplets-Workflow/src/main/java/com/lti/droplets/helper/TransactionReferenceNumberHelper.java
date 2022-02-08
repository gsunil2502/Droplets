package com.lti.droplets.helper;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class TransactionReferenceNumberHelper {
    SimpleDateFormat sdf1;
    public TransactionReferenceNumberHelper(){
        this.sdf1 = new SimpleDateFormat("yyMMddHHmmssSSS");
    }

    public synchronized String generateTransactionReferenceNumber(){
       return  "TRN"+sdf1.format(new Date());
    }
}
