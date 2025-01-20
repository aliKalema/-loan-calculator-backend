package co.ke.resilient.loan_calculator.payload.security;

import co.ke.resilient.loan_calculator.model.security.User;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private boolean mfaRequired;
    private boolean authValid;
    private boolean tokenValid;
    private String message;
    private User user;
    private List<String> roles = new ArrayList<>();
}
