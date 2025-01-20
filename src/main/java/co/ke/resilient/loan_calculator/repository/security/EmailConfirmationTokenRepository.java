package co.ke.resilient.loan_calculator.repository.security;

import co.ke.resilient.loan_calculator.model.security.EmailConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EmailConfirmationTokenRepository extends JpaRepository<EmailConfirmationToken, UUID> {
    Optional<EmailConfirmationToken> findByToken(String token);

    String deleteByToken(String token);
}