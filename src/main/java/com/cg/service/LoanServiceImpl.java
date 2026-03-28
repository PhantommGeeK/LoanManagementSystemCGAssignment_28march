package com.cg.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.entity.Loan;
import com.cg.exception.DuplicateLoanApplicationException;
import com.cg.exception.InvalidLoanAmountException;
import com.cg.exception.LoanNotFoundException;
import com.cg.repository.LoanRepository;

@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Override
    public Loan createLoan(Loan loan) {
        if (loan.getLoanAmount() <= 0 || loan.getLoanAmount() > 5000000) {
            throw new InvalidLoanAmountException("Loan amount must be between 1 and 5000000");
        }

        List<Loan> pendingLoans = loanRepository.findByApplicantNameAndStatus(
                loan.getApplicantName(), "PENDING");

        if (!pendingLoans.isEmpty()) {
            throw new DuplicateLoanApplicationException(
                    "Applicant already has a loan application in PENDING status");
        }

        loan.setStatus("PENDING");
        return loanRepository.save(loan);
    }

    @Override
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    @Override
    public Loan getLoanById(Long id) {
        return loanRepository.findById(id)
                .orElseThrow(() -> new LoanNotFoundException("Loan not found with ID: " + id));
    }

    @Override
    public Loan updateLoanStatus(Long id, Map<String, String> statusMap) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new LoanNotFoundException("Loan not found with ID: " + id));

        loan.setStatus(statusMap.get("status"));
        return loanRepository.save(loan);
    }
}