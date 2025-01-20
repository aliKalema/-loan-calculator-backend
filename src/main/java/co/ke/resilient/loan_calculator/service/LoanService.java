package co.ke.resilient.loan_calculator.service;

import co.ke.resilient.loan_calculator.model.LoanBreakdown;
import co.ke.resilient.loan_calculator.model.LoanRequest;
import co.ke.resilient.loan_calculator.model.PaymentDetail;
import co.ke.resilient.loan_calculator.model.security.User;
import co.ke.resilient.loan_calculator.repository.LoanBreakdownRepository;
import co.ke.resilient.loan_calculator.service.security.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoanService {
    private final LoanBreakdownRepository loanBreakdownRepository;
    private final UserService userService;

    public List<LoanBreakdown> getLoans() {
        User user =  userService.getSessionUser();
        return loanBreakdownRepository.findByUserOrderByIdDesc(user);
    }

    public LoanBreakdown calculateBreakDown(LoanRequest request) {
        User user = userService.getSessionUser();
        BigDecimal principal = request.getPrincipal();
        BigDecimal interestRate = request.getInterest();
        int tenureInMonths = request.getTerm();

        // Calculate monthly interest rate: interestRate / 12 / 100
        BigDecimal monthlyInterestRate = interestRate.divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);

        // Calculate EMI: (P * r * (1 + r)^n) / ((1 + r)^n - 1)
        BigDecimal numerator = principal.multiply(monthlyInterestRate).multiply(
                BigDecimal.ONE.add(monthlyInterestRate).pow(tenureInMonths)
        );
        BigDecimal denominator = BigDecimal.ONE.add(monthlyInterestRate).pow(tenureInMonths).subtract(BigDecimal.ONE);
        BigDecimal emi = numerator.divide(denominator, 2, RoundingMode.HALF_UP);

        // Calculate total payment and total interest
        BigDecimal totalPayment = emi.multiply(BigDecimal.valueOf(tenureInMonths));
        BigDecimal totalInterest = totalPayment.subtract(principal);

        // Build payment schedule
        List<PaymentDetail> schedule = new ArrayList<>();
        BigDecimal remainingBalance = principal;

        for (int month = 1; month <= tenureInMonths; month++) {
            // Interest Paid: remainingBalance * monthlyInterestRate
            BigDecimal interestPaid = remainingBalance.multiply(monthlyInterestRate).setScale(2, RoundingMode.HALF_UP);

            // Principal Paid: EMI - interestPaid
            BigDecimal principalPaid = emi.subtract(interestPaid).setScale(2, RoundingMode.HALF_UP);

            // Remaining Balance: remainingBalance - principalPaid
            remainingBalance = remainingBalance.subtract(principalPaid).setScale(2, RoundingMode.HALF_UP);

            // Create PaymentDetail
            PaymentDetail paymentDetail = new PaymentDetail();
            paymentDetail.setMonth(month);
            paymentDetail.setPrincipalPaid(principalPaid);
            paymentDetail.setInterestPaid(interestPaid);
            paymentDetail.setRemainingBalance(remainingBalance.max(BigDecimal.ZERO));

            schedule.add(paymentDetail);
        }

        // Create LoanBreakdown entity
        LoanBreakdown breakdown = new LoanBreakdown();
        breakdown.setUser(user);
        breakdown.setLoanRequest(request);
        breakdown.setMonthlyPayment(emi);
        breakdown.setTotalInterest(totalInterest);
        breakdown.setTotalPayment(totalPayment);
        breakdown.setPaymentSchedule(schedule);

        schedule.forEach(payment -> payment.setLoanBreakdown(breakdown));

        return loanBreakdownRepository.save(breakdown);
    }
}
