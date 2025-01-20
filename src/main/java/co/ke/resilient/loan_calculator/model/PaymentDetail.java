package co.ke.resilient.loan_calculator.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class PaymentDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer month;

    private BigDecimal principalPaid;

    private BigDecimal interestPaid;

    private BigDecimal remainingBalance;

    @ManyToOne
    @JoinColumn(name = "loan_breakdown_id")
    @JsonBackReference
    private LoanBreakdown loanBreakdown;
}
