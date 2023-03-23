package com.mindhub.demo.repositories;


import com.mindhub.demo.models.Client;
import com.mindhub.demo.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface LoanRepository extends JpaRepository<Loan, Long> {
}
