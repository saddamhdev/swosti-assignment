package com.swosti.v1.controller.swosti;

import com.swosti.v1.model.swosti.LoanLedger;
import com.swosti.v1.service.swosti.LoanLedgerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loan-ledger")
@RequiredArgsConstructor
public class LoanLedgerController {

    private final LoanLedgerService loanLedgerService;

    @PostMapping
    public ResponseEntity<LoanLedger> create(
            @RequestBody LoanLedger loanLedger
    ) {
        return ResponseEntity.ok(
                loanLedgerService.create(loanLedger)
        );
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(
                loanLedgerService.findAll()
        );
    }
}
