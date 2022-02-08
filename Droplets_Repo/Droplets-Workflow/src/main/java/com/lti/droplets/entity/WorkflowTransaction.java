package com.lti.droplets.entity;


import lombok.Data;
import org.springframework.http.HttpStatus;

import javax.persistence.*;

@Entity
@Data
public class WorkflowTransaction {

    @SequenceGenerator(
            name = "workflow_sequence",
            sequenceName = "workflow_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "workflow_sequence"
    )

    private Long Id;
    private String transactionReferenceNumber;
    private String operation;
    private String service;
    private String status;
    private Integer responseCode;
}
