package com.lti.droplets.goals.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lti.droplets.goals.model.GoalsModel;


@Repository
public interface GoalsRepository extends JpaRepository<GoalsModel, Long>{

	List<GoalsModel> findAllByCustomerId(Long customerId);
	GoalsModel findByGoalId(Long dropletId);
}
