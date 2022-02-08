package com.lti.droplets.usermanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lti.droplets.corelib.exception.DropletsException;
import com.lti.droplets.corelib.model.DropletsRequestModel;
import com.lti.droplets.corelib.model.DropletsResponseModel;
import com.lti.droplets.usermanager.entity.DropletsModel;
import com.lti.droplets.usermanager.entity.UserModel;
import com.lti.droplets.usermanager.service.DropletsCrudService;
import com.lti.droplets.usermanager.service.NewDropletsOnboardingService;
import com.lti.droplets.usermanager.service.UserActivationByTokenService;
import com.lti.droplets.usermanager.service.UserManagerService;

@RestController
@RequestMapping("/api/v1/userManager")
public class UserManagerController {
	@Autowired
	private UserManagerService userManagerService;

	@Autowired
	private UserActivationByTokenService userActivationByTokenService;
	
	@Autowired
	private NewDropletsOnboardingService newDropletsOnboardingService;
	
	@Autowired
	private DropletsCrudService dropletsCrudService;

	@PostMapping("/newUserRegistration")
	public DropletsResponseModel newUserRegistration(@RequestBody DropletsRequestModel dropletsRequestModel )throws DropletsException
	{
		return userManagerService.newUserRegistration(dropletsRequestModel);
	}
	
	@PostMapping("/newDropletsOnboarding")
	public DropletsResponseModel newDropletsOnboarding(@RequestBody DropletsRequestModel dropletsRequestModel )throws DropletsException
	{
		return newDropletsOnboardingService.newDropletsOnboarding(dropletsRequestModel);
	}

	@PutMapping("/modifyUser")
	public DropletsResponseModel modifyUser(@RequestBody DropletsRequestModel dropletsRequestModel) throws  DropletsException
	{
		return userManagerService.modifyUser(dropletsRequestModel);
	}

	@GetMapping("/activateUser")
	public DropletsResponseModel activateUser(@RequestParam Long customerId) throws DropletsException{
		return userManagerService.activateUser(customerId);
	}

	@GetMapping("/userAccountActivationUsingToken")
	public DropletsResponseModel userAccountActivationUsingToken(@RequestParam String token) throws DropletsException{
		return userActivationByTokenService.confirmToken(token);
	}
	@GetMapping("/getUserByCustomerId")
	public DropletsResponseModel getUserByCustomerId(@RequestParam Long customerId) throws DropletsException{
		return userManagerService.getUserByCustomerId(customerId);
	}
	
	@GetMapping("/findAllDropletsByCustomerId")
	public DropletsResponseModel findAllDropletsByCustomerId(@RequestParam Long customerId) throws DropletsException{
		return dropletsCrudService.findAllDropletsByCustomerId(customerId);
	}
	
	

	@GetMapping("/test")
	public DropletsRequestModel testing()
	{
		DropletsRequestModel dropletsRequestModel = new DropletsRequestModel();
		dropletsRequestModel.setRequest(new UserModel());
		return dropletsRequestModel;
	}
	@GetMapping("/testPassword")
	public DropletsRequestModel testPassword()
	{
		DropletsRequestModel dropletsRequestModel = new DropletsRequestModel();
		dropletsRequestModel.setRequest(new DropletsModel());
		return dropletsRequestModel;
	}

}
