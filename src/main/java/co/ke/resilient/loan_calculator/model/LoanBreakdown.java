package co.ke.resilient.loan_calculator.model;

import co.ke.resilient.loan_calculator.model.security.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter

public class LoanBreakdown {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    LoanRequest loanRequest;

    private BigDecimal monthlyPayment;

    private BigDecimal totalInterest;

    private BigDecimal totalPayment;

    @OneToMany(mappedBy = "loanBreakdown", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PaymentDetail> paymentSchedule = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;
}
