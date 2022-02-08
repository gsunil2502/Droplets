package com.lti.droplets.usermanager.repo;

import com.lti.droplets.usermanager.entity.UserIam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserIamRepo extends JpaRepository<UserIam,Long> {

	public UserIam findByUserMobileNumber(String mobileNumber);
	public UserIam findByUserEmail(String userEmail);
}
