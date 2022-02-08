package com.lti.droplets.transactions.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lti.droplets.corelib.exception.DropletsException;
import com.lti.droplets.corelib.model.DropletsRequestModel;
import com.lti.droplets.corelib.model.DropletsResponseModel;
import com.lti.droplets.corelib.model.Status;
import com.lti.droplets.transactions.model.AllTransactions;
import com.lti.droplets.transactions.model.TransactionsModel;
import com.lti.droplets.transactions.repository.TransactionsRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class TransactionsService {

	@Autowired
	private TransactionsRepository transactionsRepository;
	public DropletsResponseModel getTransactionDetailsById(Long transactionId) throws  DropletsException{
		try{
			DropletsResponseModel dropletsResponseModel = new DropletsResponseModel();
			Optional<TransactionsModel> optionalTransactionsModel= transactionsRepository.findByTransactionId(transactionId);
			try {
				TransactionsModel transactionsModel=optionalTransactionsModel.get();
				dropletsResponseModel.setMessage("Transaction Details Fetched Successfully");
				dropletsResponseModel.setStatus(Status.SUCCESS);
				dropletsResponseModel.setResult(transactionsModel);
				return dropletsResponseModel;

			}
			catch (Exception e)
			{
				throw new DropletsException(Status.FAILURE,"No transaction available");
			}
		}
		catch (Exception e){
			throw new DropletsException(Status.FAILURE,e.getMessage());
		}

	}
	public DropletsResponseModel getTransactionDetailsByReferenceId(String trxRefNumber) throws  DropletsException{

		try{
			DropletsResponseModel dropletsResponseModel = new DropletsResponseModel();
			Optional<TransactionsModel> optionalTransactionsModel= transactionsRepository.findByTrxRefNumber(trxRefNumber);
			try {
				TransactionsModel transactionsModel=optionalTransactionsModel.get();
				dropletsResponseModel.setMessage("Transaction Details Fetched Successfully");
				dropletsResponseModel.setStatus(Status.SUCCESS);
				dropletsResponseModel.setResult(transactionsModel);
				return dropletsResponseModel;
			}
			catch (Exception e)
			{
				throw new DropletsException(Status.FAILURE,"No transaction available");
			}
		}
		catch (Exception e){
			throw new DropletsException(Status.FAILURE,e.getMessage());
		}

	}

	public DropletsResponseModel getAllTransactionsHistory(@RequestBody DropletsRequestModel dropletsRequestModel) throws DropletsException {
		DropletsResponseModel dropletsResponseModel = new DropletsResponseModel();
		try {
			AllTransactions allTransactions = new ObjectMapper().convertValue(dropletsRequestModel.getRequest(), AllTransactions.class);
			Long customerId = allTransactions.getCustomerId();
			String beneficiaryId = allTransactions.getBeneficaryId();
			Date fromDate = null, toDate = null;
			if (allTransactions.getFromDate() != null)
				fromDate = new SimpleDateFormat("yyyy-MM-dd").parse(allTransactions.getFromDate());
			if (allTransactions.getToDate() != null)
				toDate = new SimpleDateFormat("yyyy-MM-dd").parse(allTransactions.getToDate());
			if (customerId != 0) {
				if (beneficiaryId != null && fromDate != null && toDate != null) {

					List<TransactionsModel> transactionsModels = transactionsRepository.findByCustomerIdAndDateAndBeneficiaryId(customerId, beneficiaryId, fromDate, toDate);
					if (transactionsModels.isEmpty()) {
						dropletsResponseModel.setMessage("No Transactions available ");
						dropletsResponseModel.setStatus(Status.FAILURE);
						dropletsResponseModel.setResult(transactionsModels);
					} else {
						dropletsResponseModel.setMessage("Transaction Details Fetched based on beneficary Id and from Date - to Date");
						dropletsResponseModel.setStatus(Status.SUCCESS);
						dropletsResponseModel.setResult(transactionsModels);
					}
					return dropletsResponseModel;
				} else {
					if (beneficiaryId != null && fromDate == null && toDate == null) {
						List<TransactionsModel> transactionsModels = transactionsRepository.findByCustomerIdAndBeneficiaryId(customerId, beneficiaryId);
						if (transactionsModels.isEmpty()) {
							dropletsResponseModel.setMessage("No Transactions available ");
							dropletsResponseModel.setStatus(Status.FAILURE);
							dropletsResponseModel.setResult(transactionsModels);
						} else {
							dropletsResponseModel.setMessage("Transaction Details Fetched based on beneficary Id");
							dropletsResponseModel.setStatus(Status.SUCCESS);
							dropletsResponseModel.setResult(transactionsModels);
						}
						return dropletsResponseModel;

					} else {
						if (beneficiaryId == null && fromDate != null && toDate != null) {

							List<TransactionsModel> transactionsModels = transactionsRepository.findByCustomerIdAndDate(customerId, fromDate, toDate);
							if (transactionsModels.isEmpty()) {
								dropletsResponseModel.setMessage("No Transactions available ");
								dropletsResponseModel.setStatus(Status.FAILURE);
								dropletsResponseModel.setResult(transactionsModels);
							} else {
								dropletsResponseModel.setMessage("Transaction Details Fetched based on fromDate - to Date");
								dropletsResponseModel.setStatus(Status.SUCCESS);
								dropletsResponseModel.setResult(transactionsModels);
							}
							return dropletsResponseModel;

						} else {
							if (beneficiaryId == null && fromDate == null && toDate == null) {

								List<TransactionsModel> transactionsModels = transactionsRepository.findByCustomerId(customerId);
								if (transactionsModels.isEmpty()) {
									dropletsResponseModel.setMessage("No Transactions available ");
									dropletsResponseModel.setStatus(Status.FAILURE);
									dropletsResponseModel.setResult(transactionsModels);
								} else {
									dropletsResponseModel.setMessage("Transaction Details Fetched based on customerId");
									dropletsResponseModel.setStatus(Status.SUCCESS);
									dropletsResponseModel.setResult(transactionsModels);

								}
								return dropletsResponseModel;
							} else {
								if (fromDate == null) {
									dropletsResponseModel.setMessage("to date is mentioned but From Date is not mentioned,");
									dropletsResponseModel.setStatus(Status.FAILURE);
								} else if (toDate == null) {
									dropletsResponseModel.setMessage("From date is mentioned but to Date is not mentioned ");
									dropletsResponseModel.setStatus(Status.FAILURE);
								}
							}
						}
					}
				}

			} else {
				dropletsResponseModel.setStatus(Status.FAILURE);
				dropletsResponseModel.setMessage("customer Id is not passed as parameter");
			}
		}
		catch (Exception e)
		{
			throw new DropletsException(Status.FAILURE,e.getMessage());
		}
		return dropletsResponseModel;

	}
	public DropletsResponseModel saveTransaction(DropletsRequestModel dropletsRequestModel) throws DropletsException {
		DropletsResponseModel dropletsResponseModel = new DropletsResponseModel();
		try {
			TransactionsModel transactionsModel = new ObjectMapper().convertValue(dropletsRequestModel.getRequest(), TransactionsModel.class);
			transactionsModel = transactionsRepository.save(transactionsModel);
			dropletsResponseModel.setStatus(Status.SUCCESS);
			dropletsResponseModel.setResult(transactionsModel);
			dropletsResponseModel.setMessage("transaction successfull");
		} catch (Exception e) {
			e.printStackTrace();
			throw new DropletsException(Status.FAILURE, "transaction is unsuccesfull");
		}
		return dropletsResponseModel;
	}
}
