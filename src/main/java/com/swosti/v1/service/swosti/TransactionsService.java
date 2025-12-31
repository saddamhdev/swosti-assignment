package com.swosti.v1.service.swosti;

import com.swosti.v1.model.swosti.Disburse;
import com.swosti.v1.model.swosti.LoanLedger;
import com.swosti.v1.model.swosti.Transactions;
import com.swosti.v1.repository.swosti.DisburseRepo;
import com.swosti.v1.repository.swosti.LoanLedgerRepo;
import com.swosti.v1.repository.swosti.TransactionsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
@Service
@RequiredArgsConstructor
public class TransactionsService {

    private final TransactionsRepo transactionsRepo;

    @Transactional
    public Transactions create(Transactions transactions) {

        if (transactions.getVoucherNo() == null)
            throw new RuntimeException("voucherNo is required");

        if (transactions.getLedgerName() == null)
            throw new RuntimeException("ledgerName is required");

        if (transactions.getBranchId() == null)
            throw new RuntimeException("branchId is required");

        return transactionsRepo.save(transactions);
    }

    @Transactional
    public void postBranchTotals(Integer branchId, BigDecimal total) {

        List<Transactions> txns =
                transactionsRepo.findByBranchId(branchId);

        for (Transactions t : txns) {
            if ("Cash".equalsIgnoreCase(t.getLedgerName())) {
                t.setCreditAmount(total);
                t.setDebitAmount(BigDecimal.ZERO);
            }
            if ("Loan".equalsIgnoreCase(t.getLedgerName())) {
                t.setDebitAmount(total);
                t.setCreditAmount(BigDecimal.ZERO);
            }
        }
    }

    @Transactional
    public void deleteByBranchId(Integer branchId) {
        transactionsRepo.deleteAll(
                transactionsRepo.findByBranchId(branchId)
        );
    }

    @Transactional(readOnly = true)
    public List<Transactions> findAll() {
        return transactionsRepo.findAll();
    }
}

