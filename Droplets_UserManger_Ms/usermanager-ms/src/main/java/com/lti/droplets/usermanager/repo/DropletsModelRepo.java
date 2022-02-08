package com.lti.droplets.usermanager.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lti.droplets.usermanager.entity.DropletsModel;

@Repository
public interface DropletsModelRepo extends JpaRepository<DropletsModel, Long>{
   
	List<DropletsModel> findByCustomerId(Long customerId);
}
