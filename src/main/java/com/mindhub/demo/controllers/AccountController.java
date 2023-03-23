package com.mindhub.demo.controllers;

import com.mindhub.demo.dtos.AccountDTO;
import com.mindhub.demo.dtos.CardDTO;
import com.mindhub.demo.dtos.ClientDTO;
import com.mindhub.demo.models.Account;
import com.mindhub.demo.models.Card;
import com.mindhub.demo.models.Client;
import com.mindhub.demo.repositories.AccountRepository;
import com.mindhub.demo.repositories.ClientRepository;
import com.mindhub.demo.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

    @RestController
    @RequestMapping("/api")
    public class AccountController {
        @Autowired
        private AccountRepository accountRepository;

        @Autowired
        private ClientRepository clientRepository;

        @Autowired
        private TransactionRepository transactionRepository;
        //Devuelve lista de accounts.
        @GetMapping("/accounts")
        public List<AccountDTO> getAccount() {
            return accountRepository.findAll().stream().map(account -> new AccountDTO(account)).collect(toList());
        }

        //Devuelve account por parametro {id}.
        @GetMapping("/accounts/{id}")
        public AccountDTO getAccount(@PathVariable Long id) {
            return accountRepository.findById(id).map(AccountDTO::new).orElse(null);
        }

//        @GetMapping("/accounts/rico")
//        public List<AccountDTO> getAccounts7000(){
//            return this.accountRepository.findAll()
//                    .stream()
//                    .filter(account -> account.getBalance()>7000)
//                    .map(account -> new AccountDTO(account))
//                    .collect(toList());
//        }


        @DeleteMapping("/deleteAccount/{id}")
        public void deleteAccount(@PathVariable Long id){
            Optional<Account> account = accountRepository.findById(id);
            transactionRepository.deleteAll(account.get().getTransactions());
            accountRepository.deleteById(id);
        }

        @PostMapping("/clients/{id}/accounts") //localhost:8080/api/clients/1/accounts
        public ResponseEntity<Object> createAccount(@PathVariable Long id) {
            try {
                Optional<Client> client = clientRepository.findById(id);
                if(client.isPresent()) {
                    Account account = new Account(LocalDateTime.now(),0.00, client.get());
                    accountRepository.save(account);
                    return new ResponseEntity<>(HttpStatus.CREATED);
                }else {
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
                exception.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        //Devuelve account del cliente actual.
        @GetMapping("/clients/current/accounts")
        public List<AccountDTO> getAccounts(Authentication authentication){
            return clientRepository.findByEmail(authentication.getName()).get().getAccounts().stream().map(AccountDTO::new).collect(Collectors.toList());
        }

        @PostMapping("/clients/current/accounts")
        public ResponseEntity<Object> createAccount(Authentication authentication) {
            Optional<Client> client = clientRepository.findByEmail(authentication.getName());
            try {
                //Valida la cantidad de cuentas <= 3.
                if (client.get().getAccounts().stream().count() >= 3) {
                    return new ResponseEntity<>("you already have 3 accounts", HttpStatus.FORBIDDEN);
                }

                //Crea nueva cuenta.
                Account account = new Account(LocalDateTime.now(), 00.00, client.get());
                accountRepository.save(account);

                return new ResponseEntity<>("new account ", HttpStatus.CREATED);

                //Error
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
                exception.printStackTrace();
                return new ResponseEntity<>("Error creating account", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @GetMapping("/accounts/dateBefore/{creationDate}")
        public List <AccountDTO> getAccount(@PathVariable String  creationDate){
            DateTimeFormatter formatter= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return accountRepository.findByCreationDateBefore(LocalDateTime.parse(creationDate, formatter)).stream().map(AccountDTO::new).collect(toList());
        }



    }

