package com.lti.droplets.usermanager.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
public class UserActivationToken {
    @Id
    private String token;
    private Long customerId;
    private LocalDateTime tokenExpiryTime;
    private LocalDateTime tokenCreationTime;

}
