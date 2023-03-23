package com.mindhub.demo.models;


import org.hibernate.annotations.GenericGenerator;
import utils.AccountUtils;
import utils.CardUtils;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")

    private Long Id;

    private String number;

    private LocalDateTime creationDate;

    private double balance;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    private Set<Transaction> transactions = new HashSet<>();


    public Account(){

    }

    public Account(LocalDateTime creationDate, double balance, Client client) {
        this.number = "VIN" + CardUtils.generateRandomNumbers(3);
        this.creationDate = creationDate;
        this.balance = balance;
        this.client = client;
        this.number = "VIN-" + CardUtils.generateRandomNumbers(AccountUtils.generateRandomNumbersMinMax(3, 9));
    }


    public String getNumber() {
        return number;
    }


//    public String generateNumber() {
//        String newCvv = "";
//        for(int i = 0; i < 3; i++) {
//            int newNumber = (int) (Math.random() * 10);
//            newCvv += String.valueOf(newNumber);
//        }
//        return "VIN" + newCvv;
//    }


    public Long getId() {
        return Id;
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }


}
