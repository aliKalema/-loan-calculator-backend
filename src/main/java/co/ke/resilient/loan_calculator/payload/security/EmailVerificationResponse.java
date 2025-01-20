package co.ke.resilient.loan_calculator.payload.security;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailVerificationResponse {
    private boolean verified;
    private String message;
}
