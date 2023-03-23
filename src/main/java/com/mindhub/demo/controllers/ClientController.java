package com.mindhub.demo.controllers;


import com.mindhub.demo.dtos.ClientDTO;
import com.mindhub.demo.models.Account;
import com.mindhub.demo.models.Client;
import com.mindhub.demo.repositories.AccountRepository;
import com.mindhub.demo.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;


@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AccountRepository accountRepository;

    @GetMapping("/clients")
    public List<ClientDTO> getClients(){
        return clientRepository.findAll().stream().map(client -> new ClientDTO(client)).collect(toList());
    }

    @GetMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id){
        return clientRepository.findById(id).map(ClientDTO::new).orElse(null);
    }

//    @GetMapping("/clients/apellido/{lastName}")
//    public ClientDTO getClientByLastNameIgnoreCase(@PathVariable String lastName){
//        return clientRepository.findByLastNameIgnoreCase(lastName).map(ClientDTO::new).orElse(null);
//    }

//    @GetMapping("/clients/{id}/clientloans")
//    public ClientLoanDTO getClientLoans(@PathVariable Long id){
//        return clientRepository.findById(id).map(ClientLoanDTO::new).orElse(null);


    @GetMapping("/clients/email/{email}")
    public ClientDTO getClient(@PathVariable String email){
        return clientRepository.findByEmail(email).map(ClientDTO::new).orElse(null);
    }

    //--------------------------------------

    @PostMapping("/clients")
    public ResponseEntity<Object> createClient(@RequestParam String firstName, @RequestParam String lastName,
                                               @RequestParam String email, @RequestParam String password){


        // Valida si los campos no estén vacíos o nulos.
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        //Valida que el UserName (email) no esté registrado con otro usuario.
        if (clientRepository.findByEmail(email).isPresent()){
            return new ResponseEntity<>("UserName already exists", HttpStatus.FORBIDDEN);
        }


        //Creamos el cliente.
        try {
            Client client = clientRepository.save(new Client(firstName, lastName, email, passwordEncoder.encode(password)));
            Account account;
            int accounts = accountRepository.findAll().size();

            // Creamos un account inicial.
            do {
                account = new Account(LocalDateTime.now(), 00.0, client);

            } while (accountRepository.findByNumber(account.getNumber()).isPresent());

            accountRepository.save(account);
            return new ResponseEntity<>(HttpStatus.CREATED);

            //error
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("Unexpected error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //Devuelve el cliente actual.
    @GetMapping("/clients/current")
    public ClientDTO getClient(Authentication authentication) {
        Client client = this.clientRepository.findByEmail(authentication.getName()).get();
        return new ClientDTO(client);
    }


    //Devuelve el cliente por parametro {firstname}.
    @GetMapping("/clients/firstName/{firstName}")
    public List <ClientDTO> getFirstNameIgnoreCase(@PathVariable String firstName){
        return clientRepository.findByFirstNameIgnoreCase(firstName).stream().map(ClientDTO::new).collect(toList());
    }

    //Devuelve el cliente por parametro {lastName}.
    @GetMapping("/clients/lastName/{lastName}")
    public List <ClientDTO> getLastNameIgnoreCase(@PathVariable String lastName){
        return clientRepository.findByLastNameIgnoreCase(lastName).stream().map(ClientDTO::new).collect(toList());
    }

    //Devuelve el cliente por parametro {firstname} e email {email}.
    @GetMapping("/clients/firstName/{firstName}/email/{email}")
    public ClientDTO getFirstNameAndEmail(@PathVariable String firstName, String email){
        return clientRepository.findByFirstNameAndEmailIgnoreCase(firstName, email).map(ClientDTO::new).orElse(null);
    }
}
