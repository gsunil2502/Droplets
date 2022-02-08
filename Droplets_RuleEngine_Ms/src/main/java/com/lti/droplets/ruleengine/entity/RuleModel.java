package com.lti.droplets.ruleengine.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class RuleModel {

    @SequenceGenerator(
            name = "rule_sequence",
            sequenceName = "rule_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "rule_sequence"
    )

    private Long ruleId;
    private Long dropletId;
    private Long customerId;
    private String eventType;
    private String merchantName;
    private Double transactionAmountGreaterThan;
    private Date transactionFromDate;
    private Date transactionToDate;
    private Double fixedAmount;
    private Boolean isActivated;


}
