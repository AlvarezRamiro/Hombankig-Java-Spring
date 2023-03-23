package com.mindhub.demo.repositories;

import com.mindhub.demo.models.Account;
import com.mindhub.demo.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByNumber(String number);

    Optional<Account> findById(Long id);
    List<Account> findByCreationDateBefore(LocalDateTime CreationDate);

}
