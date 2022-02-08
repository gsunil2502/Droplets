package com.lti.droplets.ruleengine.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
public class TransactionsModel {

	private Long transactionId;
	private Long dropletId;
	private Long customerId;
	private String originatorName;
	private String debitAccountBranch;
	private String debitAccountNumber;
	private String debitAccountCurrency;
	private String debitAccountType;
	private String debitAccountBank;
	private String debitAccountBankCode;
	private Double debitAmount;
	private Double creditAmount;
	private Double fxRate;
	private String trxRefNumber;
	private String transactionStatus;
	private Date transactionDate;
	private String beneficiaryId;
	private String beneficiaryName;
	private String beneficiaryAccountBranch;
	private String beneficiaryAccountNumber;
	private String beneficiaryAcctCurrency;
	private String beneficiaryAcctType;
	private String beneficiaryBankName;
	private String beneficiaryBranchName;
	private String beneficiaryBankCode;
	private String beneficiarySwiftCode;
	private String originatorNarrative;
	private String beneficiaryNarrative;
	private Double originalTrxAmount;
	private String originalTrxRefNumber;
	private Date originalTrxDate;

}