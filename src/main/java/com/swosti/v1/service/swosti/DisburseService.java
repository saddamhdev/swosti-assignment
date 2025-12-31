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
public class DisburseService {
    private final DisburseRepo disburseRepo;
    private final LoanLedgerRepo loanLedgerRepo;
    private final TransactionsRepo transactionsRepo;

    @Transactional
    public Disburse create(Disburse disburse) {
        if (disburseRepo.existsById(disburse.getMemberId())) {
            throw new RuntimeException(
                    "Disburse already exists for memberId: " + disburse.getMemberId()
            );
        }
        return disburseRepo.save(disburse);
    }

    @Transactional
    public void updateAmount(Long memberId, BigDecimal correctAmount) {

        // ---------- DISBURSE ----------
        Disburse disburse = disburseRepo.findById(memberId)
                .orElseThrow(() ->
                        new RuntimeException("Member not found: " + memberId));

        disburse.setAmount(correctAmount);

        // derive branchId from disburse
        Integer branchId = disburse.getBranchId();

        // ---------- LOAN LEDGER ----------
        loanLedgerRepo.findByMemberId(memberId)
                .forEach(l -> l.setDebitAmount(correctAmount));

        // ---------- TRANSACTIONS (recalculate branch total) ----------
        BigDecimal branchTotal =
                disburseRepo.findByBranchId(branchId).stream()
                        .map(Disburse::getAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<Transactions> txns =
                transactionsRepo.findByBranchId(branchId);

        for (Transactions t : txns) {
            if ("Cash".equalsIgnoreCase(t.getLedgerName())) {
                t.setCreditAmount(branchTotal);
                t.setDebitAmount(BigDecimal.ZERO);
            }
            if ("Loan".equalsIgnoreCase(t.getLedgerName())) {
                t.setDebitAmount(branchTotal);
                t.setCreditAmount(BigDecimal.ZERO);
            }
        }
    }

    @Transactional
    public void deleteMemberData(Long memberId) {

        // ---------- DISBURSE ----------
        Disburse disburse = disburseRepo.findById(memberId)
                .orElseThrow(() ->
                        new RuntimeException("Member not found: " + memberId));

        Integer branchId = disburse.getBranchId();

        // ---------- LOAN LEDGER ----------
        loanLedgerRepo.deleteAll(
                loanLedgerRepo.findByMemberId(memberId)
        );

        // ---------- DISBURSE ----------
        disburseRepo.deleteById(memberId);

        // ---------- TRANSACTIONS (recalculate / cleanup branch) ----------
        List<Transactions> txns =
                transactionsRepo.findByBranchId(branchId);

        transactionsRepo.deleteAll(txns);
    }

    @Transactional(readOnly = true)
    public List<Disburse> findAll() {
        return disburseRepo.findAll();
    }

    @Transactional(readOnly = true)
    public Disburse findByMemberId(Long memberId) {
        return disburseRepo.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found: " + memberId));
    }

    @Transactional(readOnly = true)
    public BigDecimal calculateBranchTotal(Integer branchId) {
        return disburseRepo.findByBranchId(branchId).stream()
                .map(Disburse::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

