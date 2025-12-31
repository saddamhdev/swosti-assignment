package com.swosti.v1.repository.swosti;

import com.swosti.v1.model.swosti.LoanLedger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanLedgerRepo extends JpaRepository<LoanLedger, Long> {
    List<LoanLedger> findByMemberId(Long memberId);
}
