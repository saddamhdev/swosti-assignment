package com.swosti.v1.controller.swosti;

import com.swosti.v1.model.swosti.Transactions;
import com.swosti.v1.service.swosti.TransactionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionsController {

    private final TransactionsService transactionsService;

    @PostMapping
    public ResponseEntity<Transactions> create(
            @RequestBody Transactions transactions
    ) {
        return ResponseEntity.ok(
                transactionsService.create(transactions)
        );
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(
                transactionsService.findAll()
        );
    }
}
