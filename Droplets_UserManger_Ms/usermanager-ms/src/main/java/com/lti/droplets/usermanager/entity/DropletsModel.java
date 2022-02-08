package com.lti.droplets.usermanager.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.Data;

@Data
@Entity
public class DropletsModel {
	@SequenceGenerator(
			name = "dropletId_sequence",
			sequenceName = "dropletId_sequence",
			allocationSize = 1
	)
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "dropletId_sequence"
	)
	private Long dropletId;
	
	private Long customerId;
	private String originatorName;
	private String originatorAcctBranchCode;
	private String originatorAcctNumber;
	private String originatorAcctCurrency;
	private String originatorAcctType;
	private String originatorBankName;
	private String originatorBranchName;
	private String originatorBankCode;
	
	private Boolean isActivated=false;
}
