package com.lti.droplets.service.transactionservice;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lti.droplets.config.EndpointUrlConfig;
import com.lti.droplets.config.WorkflowConfig;
import com.lti.droplets.corelib.exception.DropletsException;
import com.lti.droplets.corelib.model.DropletsRequestModel;
import com.lti.droplets.corelib.model.DropletsResponseModel;
import com.lti.droplets.corelib.model.Status;
import com.lti.droplets.entity.WorkflowTransaction;
import com.lti.droplets.helper.TransactionReferenceNumberHelper;
import com.lti.droplets.repo.WorkflowTransactionRepo;
import com.lti.droplets.router.RestInvoker;

@Service
public class NewTransactionViaSMSService {

	@Autowired
	private WorkflowTransactionRepo workflowTransactionRepo;

	@Autowired
	private WorkflowConfig workflowConfig;

	@Autowired
	private RestInvoker restInvoker;

	@Autowired
	private TransactionReferenceNumberHelper transactionReferenceNumberHelper;

	@Transactional
	public DropletsResponseModel newTransactionViaSMS(DropletsRequestModel dropletsRequestModel)
			throws DropletsException {

		DropletsResponseModel dropletsResponseModel = null;
		Boolean successfulTrn = false;
		try {
			String referenceNumber = transactionReferenceNumberHelper.generateTransactionReferenceNumber();
			
			/*
			 * Step 1 Save transaction in Transaction DB
			 */

			WorkflowTransaction workflowTransactionStep1 = new WorkflowTransaction();
			workflowTransactionStep1.setTransactionReferenceNumber(referenceNumber);
			ResponseEntity<DropletsResponseModel> dropletsResponseModelResponseEntity = restInvoker
					.invoke(workflowConfig.getTransactionManagerMsIpAddress()+EndpointUrlConfig.TRANSACTIONMANAGER_SAVE_TRANSACTION, dropletsRequestModel);

			dropletsResponseModel = dropletsResponseModelResponseEntity.getBody();
			workflowTransactionStep1.setService("newTransactionViaSMS");
			workflowTransactionStep1.setOperation("Save_Transaction_in_transaction_MS_DB");
			workflowTransactionStep1.setStatus(dropletsResponseModel.getStatus().toString());
			workflowTransactionStep1.setResponseCode(dropletsResponseModelResponseEntity.getStatusCodeValue());

			workflowTransactionRepo.save(workflowTransactionStep1);

			/*
			 * Step 2 Check transaction eligibility for rule
			 */
			WorkflowTransaction workflowTransactionStep2 = new WorkflowTransaction();
			Boolean isEligible = false;
			Double saving = 0.0;
			dropletsResponseModelResponseEntity = restInvoker.invoke(workflowConfig.getRulesMsIpAddress()
					+ EndpointUrlConfig.RULES_CHECK_TRANSACTION_ELIGIBILITY_FOR_RULE, dropletsRequestModel);
			dropletsResponseModel = dropletsResponseModelResponseEntity.getBody();
			if (dropletsResponseModel.getStatus()==Status.SUCCESS) {
				JsonNode jsonNode = new ObjectMapper().convertValue(dropletsResponseModel.getResult(), JsonNode.class);
				//Object result = dropletsResponseModel.getResult();
				//JsonNode jsonNode = (JsonNode) result;
				isEligible = jsonNode.get("ruleEligibility").asBoolean();
				saving = jsonNode.get("savings").asDouble();
				workflowTransactionStep2.setTransactionReferenceNumber(referenceNumber);
				workflowTransactionStep2.setService("newTransactionViaSMS");
				workflowTransactionStep2.setOperation("Check_transaction_eligibility_for_rule");
				workflowTransactionStep2.setStatus(dropletsResponseModel.getStatus().toString());
				workflowTransactionStep2.setResponseCode(dropletsResponseModelResponseEntity.getStatusCodeValue());

				workflowTransactionRepo.save(workflowTransactionStep2);

			} else {
				workflowTransactionStep2.setService("newTransactionViaSMS");
				workflowTransactionStep2.setOperation("Check_transaction_eligibility_for_rule");
				workflowTransactionStep2.setStatus(dropletsResponseModel.getStatus().toString());
				workflowTransactionStep2.setResponseCode(dropletsResponseModelResponseEntity.getStatusCodeValue());

				workflowTransactionRepo.save(workflowTransactionStep2);

				throw new DropletsException(Status.FAILURE,
						"Error from Rule Engine: " + dropletsResponseModel.getMessage());
			}
			/*
			 * Step 3 if transaction is eligible do droplet transaction
			 */
			WorkflowTransaction workflowTransactionStep3 = new WorkflowTransaction();
			workflowTransactionStep3.setTransactionReferenceNumber(referenceNumber);
			if (isEligible) {
				// send for droplet transaction

				workflowTransactionStep3.setService("newTransactionViaSMS");
				workflowTransactionStep3.setOperation("send_for_droplet_transaction");
				workflowTransactionStep3.setStatus("DUMMY");
				workflowTransactionStep3.setResponseCode(0000);

				workflowTransactionRepo.save(workflowTransactionStep3);
			}

			successfulTrn = true;
			/*
			 * Step 4 if goal is found update goal
			 */

			/*
			 * step 5 send notification
			 */
			
			
			
			if(isEligible) {
				dropletsResponseModel.setResult("Transaction Reference Number: "+referenceNumber);
				dropletsResponseModel.setStatus(Status.SUCCESS);
				dropletsResponseModel.setMessage("Transaction is eligible for droplets saving. Total saving: "+saving);
				return dropletsResponseModel;
			}
			else {
				dropletsResponseModel.setResult("Transaction Reference Number: "+referenceNumber);
				dropletsResponseModel.setStatus(Status.FAILURE);
				dropletsResponseModel.setMessage("Transaction is not eligible for droplets saving.");
				
				return dropletsResponseModel;
			}

			

		} catch (Exception e) {
			e.printStackTrace();
			dropletsResponseModel.setResult(null);
			dropletsResponseModel.setStatus(Status.FAILURE);
			dropletsResponseModel.setMessage(e.getMessage());
			
			return dropletsResponseModel;
		}
	}
}
