package com.lti.droplets.usermanager.repo;


import com.lti.droplets.usermanager.entity.UserActivationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserActivationTokenRepo extends JpaRepository<UserActivationToken, String> {

}
