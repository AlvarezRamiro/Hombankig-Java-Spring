package com.mindhub.demo.repositories;


import com.mindhub.demo.models.Account;
import com.mindhub.demo.models.Client;
import com.mindhub.demo.models.Transaction;
import com.mindhub.demo.models.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByDateBetween(LocalDateTime date1, LocalDateTime date2);
    List<Transaction> findByAmountBetween(double amount1, double amount2);

    List<Transaction> findTransactionByType(TransactionType type);

    Optional<Object> findByAccount(Account account);
}
