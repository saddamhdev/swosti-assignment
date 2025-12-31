package com.swosti.v1.repository.swosti;

import com.swosti.v1.model.swosti.Disburse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface DisburseRepo extends JpaRepository<Disburse, Long> {
    List<Disburse> findByBranchId(Integer branchId);
}
