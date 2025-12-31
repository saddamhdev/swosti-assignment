package com.swosti.v1.repository.swosti;

import com.swosti.v1.model.swosti.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionsRepo extends JpaRepository<Transactions, Long> {
    List<Transactions> findByBranchId(Integer branchId);
}

