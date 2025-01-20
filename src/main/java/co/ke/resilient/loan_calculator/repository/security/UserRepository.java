package co.ke.resilient.loan_calculator.repository.security;

import co.ke.resilient.loan_calculator.model.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);
  }