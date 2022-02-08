package com.lti.droplets.transactions.model;

import lombok.Data;

import javax.persistence.Entity;
import java.util.Date;

@Data
public class AllTransactions {
    private Long customerId;
    private String beneficaryId;
    private String fromDate;
    private String toDate;
}
