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
public class LoanAccountingService {

    private final DisburseRepo disburseRepo;
    private final LoanLedgerRepo loanLedgerRepo;
    private final TransactionsRepo transactionsRepo;

    /**
     * Fix wrong amounts for member 101 and 102
     * 101 -> 10000
     * 102 -> 15000
     * Branch 100 total -> 25000
     */
    @Transactional
    public void fixWrongAmountsFor101And102() {

        // ---------- DISBURSE ----------
        Disburse d101 = disburseRepo.findById(101L)
                .orElseThrow(() -> new RuntimeException("Member 101 not found"));
        Disburse d102 = disburseRepo.findById(102L)
                .orElseThrow(() -> new RuntimeException("Member 102 not found"));

        d101.setAmount(BigDecimal.valueOf(10000));
        d102.setAmount(BigDecimal.valueOf(15000));

        // ---------- LOAN LEDGER ----------
        loanLedgerRepo.findByMemberId(101L)
                .forEach(l -> l.setDebitAmount(BigDecimal.valueOf(10000)));

        loanLedgerRepo.findByMemberId(102L)
                .forEach(l -> l.setDebitAmount(BigDecimal.valueOf(15000)));

        // ---------- TRANSACTIONS (Branch 100) ----------
        BigDecimal total = BigDecimal.valueOf(25000);

        List<Transactions> txns = transactionsRepo.findByBranchId(100);
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
        // No save() required -> Hibernate dirty checking
    }

    /**
     * Delete all accounting data of member 201
     */
    @Transactional
    public void deleteMember201Data() {

        // ---------- LOAN LEDGER ----------
        List<LoanLedger> ledgers = loanLedgerRepo.findByMemberId(201L);
        loanLedgerRepo.deleteAll(ledgers);

        // ---------- DISBURSE ----------
        disburseRepo.deleteById(201L);

        // ---------- TRANSACTIONS (Branch 200) ----------
        List<Transactions> txns = transactionsRepo.findByBranchId(200);
        transactionsRepo.deleteAll(txns);
    }

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


    @Transactional(readOnly = true)
    public List<Disburse> getAllDisburse() {
        return disburseRepo.findAll();
    }

    @Transactional(readOnly = true)
    public List<LoanLedger> getAllLoanLedger() {
        return loanLedgerRepo.findAll();
    }

    @Transactional(readOnly = true)
    public List<Transactions> getAllTransactions() {
        return transactionsRepo.findAll();
    }

    @Transactional
    public Disburse createDisburse(Disburse disburse) {

        if (disburseRepo.existsById(disburse.getMemberId())) {
            throw new RuntimeException(
                    "Disburse already exists for memberId: " + disburse.getMemberId()
            );
        }

        return disburseRepo.save(disburse);
    }

    @Transactional
    public LoanLedger createLoanLedger(LoanLedger loanLedger) {

        if (loanLedger.getMemberId() == null) {
            throw new RuntimeException("memberId is required");
        }

        if (loanLedger.getDebitAmount() == null && loanLedger.getCreditAmount() == null) {
            throw new RuntimeException("Either debitAmount or creditAmount must be provided");
        }

        return loanLedgerRepo.save(loanLedger);
    }

    @Transactional
    public Transactions createTransaction(Transactions transactions) {

        if (transactions.getVoucherNo() == null) {
            throw new RuntimeException("voucherNo is required");
        }

        if (transactions.getLedgerName() == null) {
            throw new RuntimeException("ledgerName is required");
        }

        if (transactions.getBranchId() == null) {
            throw new RuntimeException("branchId is required");
        }

        return transactionsRepo.save(transactions);
    }

}
