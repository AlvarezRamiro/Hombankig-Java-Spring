package com.mindhub.demo.dtos;

import com.mindhub.demo.models.Account;
import com.mindhub.demo.models.Transaction;
import com.mindhub.demo.models.TransactionType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class TransactionDTO {

    private Long id;
    private TransactionType type;
    private double amount;
    private String description;
    private LocalDateTime date;

    public TransactionDTO(TransactionType type, double amount,
                          String description, LocalDateTime date) {
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    public TransactionDTO(Transaction transaction){
        this.id = transaction.getId();
        this.type = transaction.getType();
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
        this.date = transaction.getDate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {

        this.amount = amount;
    }
// descripción + type
    public String getDescription() {
        String descripcionFinal;
        descripcionFinal = this.description;

        switch (this.type){
            case CREDIT : descripcionFinal= descripcionFinal + "- Credito";
                break;
            case DEBIT:  descripcionFinal= descripcionFinal + "- Debito";
                break;
            default:descripcionFinal= descripcionFinal + "- error";
        }

        if (this.date.toLocalDate().isEqual(LocalDate.now())) {
            descripcionFinal = descripcionFinal + "- Actual";
        }else if (this.date.toLocalDate().isBefore(LocalDate.now())) {
            descripcionFinal = descripcionFinal + "- Anterior";
        } else if (this.date.toLocalDate().isAfter(LocalDate.now())) {
            descripcionFinal = descripcionFinal + "- Marty McFly?";
        }

        return descripcionFinal;

    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
