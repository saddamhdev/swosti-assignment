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

public class StaticService {
    private final DisburseRepo disburseRepo;
    private final LoanLedgerRepo loanLedgerRepo;
    private final TransactionsRepo transactionsRepo;
    /**
     * Optional: Insert initial data (for test / setup)
     */
    @Transactional
    public void insertInitialData() {

        disburseRepo.save(new Disburse(101L, BigDecimal.valueOf(5000), 100));
        disburseRepo.save(new Disburse(102L, BigDecimal.valueOf(10000), 100));
        disburseRepo.save(new Disburse(201L, BigDecimal.valueOf(20000), 200));

        loanLedgerRepo.save(new LoanLedger(null, 101L, BigDecimal.valueOf(5000), BigDecimal.ZERO, "DR-CH-1"));
        loanLedgerRepo.save(new LoanLedger(null, 102L, BigDecimal.valueOf(10000), BigDecimal.ZERO, "DR-CH-1"));
        loanLedgerRepo.save(new LoanLedger(null, 201L, BigDecimal.valueOf(20000), BigDecimal.ZERO, "DR-CH-1"));

        transactionsRepo.save(new Transactions(null, "DR-CH-1", BigDecimal.ZERO, BigDecimal.valueOf(15000), "Cash", 100));
        transactionsRepo.save(new Transactions(null, "DR-CH-1", BigDecimal.valueOf(15000), BigDecimal.ZERO, "Loan", 100));
        transactionsRepo.save(new Transactions(null, "DR-CH-1", BigDecimal.ZERO, BigDecimal.valueOf(20000), "Cash", 200));
        transactionsRepo.save(new Transactions(null, "DR-CH-1", BigDecimal.valueOf(20000), BigDecimal.ZERO, "Loan", 200));
    }
}
