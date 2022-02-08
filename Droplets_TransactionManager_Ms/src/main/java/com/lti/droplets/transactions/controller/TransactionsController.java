package com.lti.droplets.transactions.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lti.droplets.corelib.model.DropletsRequestModel;
import com.lti.droplets.corelib.model.Status;
import com.lti.droplets.transactions.model.AllTransactions;
import com.lti.droplets.transactions.model.TransactionsModel;
import com.lti.droplets.transactions.repository.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import com.lti.droplets.corelib.exception.DropletsException;
import com.lti.droplets.corelib.model.DropletsResponseModel;
import com.lti.droplets.transactions.service.TransactionsService;

import java.text.ParseException;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/transactionDetails")
public class TransactionsController {

	@Autowired
	private TransactionsService transactionsService;
	@Autowired
	private TransactionsRepository transactionsRepository;
	@GetMapping("/getTransactionDetailsById")
	public DropletsResponseModel getTransactionDetailsById(@RequestParam Long transactionId)throws DropletsException{
		return transactionsService.getTransactionDetailsById(transactionId);
	}
	@GetMapping("/getTransactionDetailsByReferenceId")
	public DropletsResponseModel getTransactionDetailsByReferenceId(@RequestParam String trxRefNumber)throws DropletsException{
		return transactionsService.getTransactionDetailsByReferenceId(trxRefNumber);
	}

	@PostMapping("/getAllTransactionsHistory")
	public  DropletsResponseModel getAllTransactionsHistory(@RequestBody DropletsRequestModel dropletsRequestModel ) throws DropletsException, ParseException {
		return transactionsService.getAllTransactionsHistory(dropletsRequestModel);
	}

	@GetMapping("/testId")
	public DropletsRequestModel transcation()
	{
		DropletsRequestModel dropletsRequestModel = new DropletsRequestModel();
		dropletsRequestModel.setRequest(new TransactionsModel());
		return dropletsRequestModel;
	}
	@GetMapping("/emptyTransaction")
	public DropletsRequestModel emptyTransaction()
	{
		DropletsRequestModel dropletsRequestModel = new DropletsRequestModel();
		dropletsRequestModel.setRequest(new AllTransactions());
		return dropletsRequestModel;
	}

	@PostMapping("/saveTransaction")
	public DropletsResponseModel saveTransaction(@RequestBody DropletsRequestModel dropletsRequestModel ) throws DropletsException {
		return transactionsService.saveTransaction(dropletsRequestModel);
	}

}