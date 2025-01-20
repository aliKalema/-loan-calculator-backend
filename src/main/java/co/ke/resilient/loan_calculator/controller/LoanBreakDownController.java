package co.ke.resilient.loan_calculator.controller;

import co.ke.resilient.loan_calculator.model.LoanBreakdown;
import co.ke.resilient.loan_calculator.model.LoanRequest;
import co.ke.resilient.loan_calculator.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/loan-breakdowns")
public record LoanBreakDownController(LoanService loanService) {

    @GetMapping
    public List<LoanBreakdown> getLoans(){
        return loanService.getLoans();
    }

    @PostMapping
    public LoanBreakdown calculateLoanBreakdown(@RequestBody @Valid LoanRequest loanRequest) {
        return loanService.calculateBreakDown(loanRequest);
    }
}
