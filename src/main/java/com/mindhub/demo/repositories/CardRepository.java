package com.mindhub.demo.repositories;

import com.mindhub.demo.models.Card;
import com.mindhub.demo.models.Client;
import com.mindhub.demo.models.ClientLoan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {

    Optional<Card> findById(Long id);
}
