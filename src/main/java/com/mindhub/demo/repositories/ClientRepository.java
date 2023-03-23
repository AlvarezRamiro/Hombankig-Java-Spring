package com.mindhub.demo.repositories;

import com.mindhub.demo.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;


@RepositoryRestResource
public interface ClientRepository extends JpaRepository<Client, Long> {

List<Client> findByLastNameIgnoreCase(String lastName);

List<Client> findByFirstNameIgnoreCase(String firstName);

Optional<Client> findByEmail(String email);

Optional<Client> findByFirstNameAndEmailIgnoreCase(String firstName, String email);

}
