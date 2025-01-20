package co.ke.resilient.loan_calculator.repository;

import co.ke.resilient.loan_calculator.model.LoanBreakdown;
import co.ke.resilient.loan_calculator.model.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanBreakdownRepository extends JpaRepository<LoanBreakdown, Long> {
    List<LoanBreakdown> findByUserOrderByIdDesc(User user);
}
