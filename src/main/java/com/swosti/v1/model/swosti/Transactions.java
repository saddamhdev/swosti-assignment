package com.swosti.v1.model.swosti;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(
        name = "transactions",
        indexes = {
                @Index(name = "idx_tx_voucher", columnList = "voucher_no"),
                @Index(name = "idx_tx_branch", columnList = "branch_id"),
                @Index(name = "idx_tx_ledger", columnList = "ledger_name")
        }
)
@Data
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String voucherNo;
    private BigDecimal debitAmount;
    private BigDecimal creditAmount;
    private String ledgerName;
    private Integer branchId;

    public Transactions() {}

    public Transactions(Long id, String voucherNo,
                        BigDecimal debitAmount, BigDecimal creditAmount,
                        String ledgerName, Integer branchId) {
        this.id = id;
        this.voucherNo = voucherNo;
        this.debitAmount = debitAmount;
        this.creditAmount = creditAmount;
        this.ledgerName = ledgerName;
        this.branchId = branchId;
    }
}
