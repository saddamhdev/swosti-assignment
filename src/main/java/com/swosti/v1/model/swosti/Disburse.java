package com.swosti.v1.model.swosti;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(
        name = "disburse",
        indexes = {
                @Index(name = "idx_disburse_branch", columnList = "branch_id")
        }
)
@Data
public class Disburse {

    @Id
    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private Integer branchId;

    public Disburse() {}

    public Disburse(Long memberId, BigDecimal amount, Integer branchId) {
        this.memberId = memberId;
        this.amount = amount;
        this.branchId = branchId;
    }
}
