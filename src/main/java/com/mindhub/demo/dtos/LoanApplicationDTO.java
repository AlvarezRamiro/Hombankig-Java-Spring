package com.mindhub.demo.dtos;

import com.mindhub.demo.models.Account;
import com.mindhub.demo.models.ClientLoan;

public class LoanApplicationDTO {

    private Long loanId;
    private double amount;
    private int payments;
    private String toAccountNumber;

    private double maxAmount;


    public LoanApplicationDTO(ClientLoan clientLoan, Account account) {
        this.loanId = clientLoan.getId();
        this.amount = clientLoan.getLoan().getId();
        this.payments = clientLoan.getPayment();
        this.toAccountNumber = account.getNumber();
        this.maxAmount = clientLoan.getLoan().getMaxAmount();
    }

    public LoanApplicationDTO() {
    }

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getPayments() {
        return payments;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }

    public String getToAccountNumber() {
        return toAccountNumber;
    }

    public void setToAccountNumber(String toAccountNumber) {
        this.toAccountNumber = toAccountNumber;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(double maxAmount) {
        this.maxAmount = maxAmount;
    }
}
