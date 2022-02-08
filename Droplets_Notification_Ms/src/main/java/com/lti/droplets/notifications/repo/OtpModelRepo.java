package com.lti.droplets.notifications.repo;

import com.lti.droplets.notifications.entity.OtpModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OtpModelRepo extends JpaRepository<OtpModel, Long> {
}
