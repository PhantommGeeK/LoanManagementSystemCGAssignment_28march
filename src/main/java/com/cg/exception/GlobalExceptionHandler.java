package com.cg.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidLoanAmountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidLoanAmount(InvalidLoanAmountException ex) {
        return new ErrorResponse("InvalidLoanAmountException", ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(DuplicateLoanApplicationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDuplicateLoan(DuplicateLoanApplicationException ex) {
        return new ErrorResponse("DuplicateLoanApplicationException", ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(LoanNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleLoanNotFound(LoanNotFoundException ex) {
        return new ErrorResponse("LoanNotFoundException", ex.getMessage(), LocalDateTime.now());
    }
}