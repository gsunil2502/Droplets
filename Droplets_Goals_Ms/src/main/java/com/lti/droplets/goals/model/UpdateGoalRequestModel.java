package com.lti.droplets.goals.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class UpdateGoalRequestModel {
    @Id
    private Long UpdateGoalid;
    private String trxRefNumber;
    private Long goalId;
    //private Long dropletId;
    private Double savingAmount;
    private Double savingPercentage=0.0;
    //private Boolean isUpdated=false;
}
