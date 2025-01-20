package co.ke.resilient.loan_calculator.model.security;

import lombok.Data;

@Data
public class SetPasswordRequest {
    private String token;
    private String password;
}
