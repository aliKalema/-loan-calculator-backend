package co.ke.resilient.loan_calculator.payload.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordRequest {
    private String token;

    private String password;
}
