package com.lti.droplets.repo;

import com.lti.droplets.entity.WorkflowTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkflowTransactionRepo extends JpaRepository<WorkflowTransaction, Long> {
}
