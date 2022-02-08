package com.lti.droplets.usermanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lti.droplets.corelib.exception.DropletsException;
import com.lti.droplets.corelib.model.DropletsResponseModel;
import com.lti.droplets.corelib.model.Status;
import com.lti.droplets.usermanager.entity.DropletsModel;
import com.lti.droplets.usermanager.repo.DropletsModelRepo;

@Service
public class DropletsCrudService {

	@Autowired
	private DropletsModelRepo dropletsModelRepo;
	
	public DropletsResponseModel findAllDropletsByCustomerId(Long customerId) throws DropletsException {
		try {
			DropletsResponseModel dropletsResponseModel = new DropletsResponseModel();
			List<DropletsModel> dropletsModelList = dropletsModelRepo.findByCustomerId(customerId);
			if(dropletsModelList.isEmpty()) {
				throw new DropletsException(Status.FAILURE, "No Droplets Account found for given customer Id");
			}
			dropletsResponseModel.setResult(dropletsModelList);
			dropletsResponseModel.setMessage("Successfully retrived droplets account / accounts");
			dropletsResponseModel.setStatus(Status.SUCCESS);
			return dropletsResponseModel;
		}
		catch(Exception e) {
			throw new DropletsException(Status.FAILURE, e.getMessage());
		}
	}
}
