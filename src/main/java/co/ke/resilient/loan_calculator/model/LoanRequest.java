package co.ke.resilient.loan_calculator.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Embeddable
public class LoanRequest {
    @NotNull(message = "Principal amount is required")
    @Positive(message = "Principal amount must be greater than 0")
    private BigDecimal principal;

    @NotNull(message = "Interest rate is required")
    @Positive(message = "Interest rate must be greater than 0")
    private BigDecimal interest;

    @NotNull(message = "Tenure in months is required")
    @Min(value = 1, message = "Tenure must be at least 1 month")
    private int term;
}
