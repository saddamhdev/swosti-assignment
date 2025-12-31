package com.swosti.v1.controller.swosti;

import com.swosti.v1.service.swosti.DisburseService;
import com.swosti.v1.service.swosti.LoanLedgerService;
import com.swosti.v1.service.swosti.StaticService;
import com.swosti.v1.service.swosti.TransactionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/static")
@RequiredArgsConstructor
public class StaticController {

    private final StaticService staticService;
    private final DisburseService disburseService;
    private final LoanLedgerService loanLedgerService;
    private  final TransactionsService transactionsService;

    /**
     * Insert initial sample data (DEV / TEST only)
     */
    @PostMapping("/init")
    public ResponseEntity<String> insertInitialData() {
        staticService.insertInitialData();
        return ResponseEntity.ok(
                "Initial data inserted successfully"
        );
    }
    @GetMapping("/all")
    public ResponseEntity<?> getAllAccountingData() {

        Map<String, Object> response = new LinkedHashMap<>();

        response.put("disburse", disburseService.findAll());
        response.put("loanLedger", loanLedgerService.findAll());
        response.put("transactions", transactionsService.findAll());

        return ResponseEntity.ok(response);
    }
}

