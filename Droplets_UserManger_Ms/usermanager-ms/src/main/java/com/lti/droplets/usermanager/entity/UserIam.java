package com.lti.droplets.usermanager.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
@Data
@Entity
public class UserIam {
    @Id
    private Long customerId;
    private String pazzword;
    private LocalDateTime lastLoginTime=null;
    private String userMobileNumber;
    private String userEmail;
   
}
