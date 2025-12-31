package com.swosti.v1.model.swosti;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(
        name = "loan_ledger",
        indexes = {
                @Index(name = "idx_ledger_member", columnList = "member_id"),
                @Index(name = "idx_ledger_voucher", columnList = "voucher_no")
        }
)
@Data
public class LoanLedger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;
    private BigDecimal debitAmount;
    private BigDecimal creditAmount;
    private String voucherNo;

    public LoanLedger() {}

    public LoanLedger(Long id, Long memberId,
                      BigDecimal debitAmount,
                      BigDecimal creditAmount,
                      String voucherNo) {
        this.id = id;
        this.memberId = memberId;
        this.debitAmount = debitAmount;
        this.creditAmount = creditAmount;
        this.voucherNo = voucherNo;
    }
}
