package com.lti.droplets.usermanager.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lti.droplets.usermanager.entity.UserModel;

public interface UserModelRepo extends JpaRepository<UserModel, Long>{

	 UserModel findByUserMobileNumber(String mobile);
	    UserModel findByUserEmail(String email);
}
