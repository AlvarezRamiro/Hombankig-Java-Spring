package com.mindhub.demo.dtos;

import com.mindhub.demo.models.ClientLoan;

public class ClientLoanDTO {
    private Long id;

    private int payments;

    private double amount;

    private Long loanId;

    private String name;

    public ClientLoanDTO(int payment, double amount) {
        this.payments = payment;
        this.amount = amount;
    }


    public ClientLoanDTO(ClientLoan clientLoan) {
        this.id = clientLoan.getId();
        this.payments = clientLoan.getPayment();
        this.amount = clientLoan.getAmount();
        this.name = clientLoan.getLoan().getName();
        this.loanId = clientLoan.getLoan().getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPayments() {
        return payments;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
