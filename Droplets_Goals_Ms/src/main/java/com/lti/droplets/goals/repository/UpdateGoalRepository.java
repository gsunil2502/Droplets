package com.lti.droplets.goals.repository;

import com.lti.droplets.goals.model.UpdateGoalRequestModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpdateGoalRepository extends JpaRepository<UpdateGoalRequestModel, Long> {

}
