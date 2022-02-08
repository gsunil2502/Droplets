package com.lti.droplets.ruleengine.repo;

import com.lti.droplets.ruleengine.entity.RuleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RuleModelRepo extends JpaRepository<RuleModel, Long> {

    public List<RuleModel> findAllByCustomerId(Long customerId);
    public List<RuleModel> findAllByDropletIdAndIsActivated(Long dropletId, Boolean isActivated);
}
