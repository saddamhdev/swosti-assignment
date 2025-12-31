package com.swosti.v1.controller.swosti;

import com.swosti.v1.model.swosti.Disburse;
import com.swosti.v1.service.swosti.DisburseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/disburse")
@RequiredArgsConstructor
public class DisburseController {

    private final DisburseService disburseService;

    @PostMapping
    public ResponseEntity<Disburse> create(@RequestBody Disburse disburse) {
        return ResponseEntity.ok(disburseService.create(disburse));
    }

    @PutMapping("/fix-amount")
    public ResponseEntity<String> fixAmount(
            @RequestParam Long memberId,
            @RequestParam BigDecimal amount
    ) {
        disburseService.updateAmount(memberId, amount);
        return ResponseEntity.ok("Amount updated for memberId: " + memberId);
    }
    /**
     * Delete all accounting data of a member
     */
    @DeleteMapping("/member/{memberId}")
    public ResponseEntity<String> deleteMember(
            @PathVariable Long memberId
    ) {

        disburseService.deleteMemberData(memberId);

        return ResponseEntity.ok(
                "All accounting data deleted for memberId: " + memberId
        );
    }
    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(disburseService.findAll());
    }
}

