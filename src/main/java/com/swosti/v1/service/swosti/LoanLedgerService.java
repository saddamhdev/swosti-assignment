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
public class LoanLedgerService {

    private final LoanLedgerRepo loanLedgerRepo;

    @Transactional
    public LoanLedger create(LoanLedger loanLedger) {

        if (loanLedger.getMemberId() == null) {
            throw new RuntimeException("memberId is required");
        }
        if (loanLedger.getDebitAmount() == null &&
                loanLedger.getCreditAmount() == null) {
            throw new RuntimeException(
                    "Either debitAmount or creditAmount must be provided");
        }

        return loanLedgerRepo.save(loanLedger);
    }

    @Transactional
    public void updateDebit(Long memberId, BigDecimal amount) {
        loanLedgerRepo.findByMemberId(memberId)
                .forEach(l -> l.setDebitAmount(amount));
    }

    @Transactional
    public void deleteByMemberId(Long memberId) {
        loanLedgerRepo.deleteAll(
                loanLedgerRepo.findByMemberId(memberId)
        );
    }

    @Transactional(readOnly = true)
    public List<LoanLedger> findAll() {
        return loanLedgerRepo.findAll();
    }
}
