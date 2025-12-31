package com.swosti.v1.controller.swosti;


import com.swosti.v1.model.swosti.Disburse;
import com.swosti.v1.model.swosti.LoanLedger;
import com.swosti.v1.model.swosti.Transactions;
import com.swosti.v1.service.swosti.LoanAccountingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/loan")
@RequiredArgsConstructor
public class LoanAccountingController {

    private final LoanAccountingService loanAccountingService;

    /**
     * Insert initial sample data
     * (For testing / setup)
     */
    @PostMapping("/init")
    public ResponseEntity<String> insertInitialData() {
        loanAccountingService.insertInitialData();
        return ResponseEntity.ok("Initial data inserted successfully");
    }

    /**
     * Fix wrong amounts for member 101 and 102
     */
    @PutMapping("/fix-amounts")
    public ResponseEntity<String> fixWrongAmounts() {
        loanAccountingService.fixWrongAmountsFor101And102();
        return ResponseEntity.ok("Wrong amounts fixed successfully");
    }

    /**
     * Delete all data of member 201
     */
    @DeleteMapping("/member/{memberId}")
    public ResponseEntity<String> deleteMember(@PathVariable Long memberId) {

        if (memberId != 201L) {
            return ResponseEntity
                    .badRequest()
                    .body("Only member 201 deletion is allowed in this operation");
        }

        loanAccountingService.deleteMember201Data();
        return ResponseEntity.ok("Member 201 data deleted successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllAccountingData() {

        Map<String, Object> response = new LinkedHashMap<>();

        response.put("disburse", loanAccountingService.getAllDisburse());
        response.put("loanLedger", loanAccountingService.getAllLoanLedger());
        response.put("transactions", loanAccountingService.getAllTransactions());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/disburse")
    public ResponseEntity<?> getAllDisburse() {
        return ResponseEntity.ok(loanAccountingService.getAllDisburse());
    }

    @GetMapping("/loan-ledger")
    public ResponseEntity<?> getAllLoanLedger() {
        return ResponseEntity.ok(loanAccountingService.getAllLoanLedger());
    }

    @GetMapping("/transactions")
    public ResponseEntity<?> getAllTransactions() {
        return ResponseEntity.ok(loanAccountingService.getAllTransactions());
    }

    @PostMapping("/disburse")
    public ResponseEntity<?> createDisburse(@RequestBody Disburse disburse) {

        Disburse saved = loanAccountingService.createDisburse(disburse);

        return ResponseEntity.ok(saved);
    }

    @PostMapping("/loan-ledger")
    public ResponseEntity<?> createLoanLedger(@RequestBody LoanLedger loanLedger) {

        LoanLedger saved = loanAccountingService.createLoanLedger(loanLedger);

        return ResponseEntity.ok(saved);
    }
    @PostMapping("/transactions")
    public ResponseEntity<?> createTransaction(@RequestBody Transactions transactions) {

        Transactions saved = loanAccountingService.createTransaction(transactions);

        return ResponseEntity.ok(saved);
    }


}
