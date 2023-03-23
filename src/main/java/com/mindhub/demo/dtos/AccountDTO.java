package com.mindhub.demo.dtos;

import com.mindhub.demo.models.Account;
import com.mindhub.demo.models.Transaction;
import utils.AccountUtils;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {
    private Long Id;
    private String number;
    private LocalDateTime creationDate;
    private double balance;

    private Set<TransactionDTO> transactions = new HashSet<>();

    public AccountDTO(Long id, String number, LocalDateTime creationDate, double balance/*, Set<TransactionDTO> transactions*/) {
        this.Id = id;
        this.number = number;
        this.creationDate = creationDate;
        this.balance = balance;
    }
    public AccountDTO(Account account) {
        this.Id = account.getId();
        this.number = account.getNumber();
        this.creationDate = account.getCreationDate();
        this.balance = account.getBalance();
//        this.balance = AccountUtils.getBalance(account);
        this.transactions = account.getTransactions().stream().map(TransactionDTO::new).collect(Collectors.toSet());
    }


    public  Set<TransactionDTO> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<TransactionDTO> transactions) {
        this.transactions = transactions;
    }

    public Long getId() {
        return Id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public double getBalance() {
        return balance;
    }


    public void setBalance(double balance) {
        this.balance = balance;
    }



}
