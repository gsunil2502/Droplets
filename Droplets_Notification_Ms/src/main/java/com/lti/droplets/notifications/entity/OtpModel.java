package com.lti.droplets.notifications.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Entity
public class OtpModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long otpId;

    private String otpValue;
    private boolean forEmail;
    private boolean forSms;

    @Email(message = "Email is not valid")
    private String userEmail;

    @Size(min = 10, max = 10, message = "mobile number should be 10 digits")
    private String userPhoneNumber;
    private Date otpCreationDate;
    private Date otpExpiryDate;
    private String operationService;
    private boolean isValidated;


}
