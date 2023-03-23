package com.mindhub.demo.controllers;


import com.mindhub.demo.dtos.AccountDTO;
import com.mindhub.demo.dtos.TransactionDTO;
import com.mindhub.demo.models.Account;
import com.mindhub.demo.models.Client;
import com.mindhub.demo.models.Transaction;
import com.mindhub.demo.models.TransactionType;
import com.mindhub.demo.repositories.AccountRepository;
import com.mindhub.demo.repositories.ClientRepository;
import com.mindhub.demo.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/transaction")
    public List<TransactionDTO> getTransaction() {
        return transactionRepository.findAll().stream().map(transaction -> new TransactionDTO(transaction)).collect(toList());
    }

    //Devuelve transacciones por parametro {id}
    @GetMapping("/transactions/{id}")
    public TransactionDTO getTransaction(@PathVariable Long id) {
        return transactionRepository.findById(id).map(TransactionDTO::new).orElse(null);
    }

    //Devuelve transacciones entre dos fechas declaradas.
    @GetMapping("/transactions/date-between/date1/{date1}/date2/{date2}")
    public List <TransactionDTO> getTransactionBetween(@PathVariable String  date1,@PathVariable String date2){
        DateTimeFormatter formatter= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return transactionRepository.findByDateBetween(LocalDateTime.parse(date1,formatter), LocalDateTime.parse(date2, formatter)).stream().map(TransactionDTO::new).collect(toList());
    }

    //Devuelve transacciones que su monto este entre dos montos declarados(amount1 y amount2).
    @GetMapping("/transactions/amount-between/amount1/{amount1}/amount2/{amount2}")
    public List <TransactionDTO> getAmountBetween(@PathVariable double amount1  ,@PathVariable double amount2){
        return transactionRepository.findByAmountBetween(amount1, amount2).stream().map(TransactionDTO::new).collect(toList());
    }


    //Devuelve transacciones por parametro {type} -> TransactionType.
    @GetMapping("/transactions/type/{type}")
    public List <TransactionDTO> getTransaction(@PathVariable TransactionType type ){
        return transactionRepository.findTransactionByType(type).stream().map(TransactionDTO::new).collect(toList());
    }

    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> getTransactions(@RequestParam double amount, @RequestParam String description, @RequestParam String fromAccountNumber, @RequestParam String toAccountNumber) {

        // Valida si los campos no estén vacíos o nulos.
        if (amount == 0 || description.isEmpty() || fromAccountNumber.isEmpty() || toAccountNumber.isEmpty()) {
            return new ResponseEntity<>("Missing data", FORBIDDEN);
        }

        // Valida que no se transfiera a la misma cuenta.
        if (fromAccountNumber.equals(toAccountNumber)) {
            return new ResponseEntity<>("No puedes transferir a una misma cuenta", FORBIDDEN);
        }

        // Valida que la cuenta origen exista.
    Optional<Account> sendingAccount = accountRepository.findByNumber(fromAccountNumber);
        if (!sendingAccount.isPresent()) {
            return new ResponseEntity<>("La cuenta origen no existe", FORBIDDEN);
        }


        Optional<Client> client = clientRepository.findByEmail(sendingAccount.get().getClient().getEmail());
        if (!client.isPresent()) {
            return new ResponseEntity<>("No puedes hacer esta operacion", FORBIDDEN);
        }

        // Valida que la cuenta destino exista.
        Optional<Account> receiverAccount = accountRepository.findByNumber(toAccountNumber);
        if (!receiverAccount.isPresent()) {
            return new ResponseEntity<>("No existe cuenta destino, verificar numero de cuenta", FORBIDDEN);
        }

        //Valida que los fondos sean suficientes.
        if (sendingAccount.get().getBalance() < amount) {
            return new ResponseEntity<>("No cuentas con los fondos suficientes para hacer esta operacion", FORBIDDEN);
        }



        Transaction transaction1 = new Transaction(TransactionType.DEBIT,-(amount),description+ " - " +receiverAccount.get().getNumber(),LocalDateTime.now(),sendingAccount.get());
        transactionRepository.save(transaction1);


        Transaction transaction2 = new Transaction(TransactionType.CREDIT,amount,description+"-"+sendingAccount.get().getNumber(), LocalDateTime.now(),receiverAccount.get());
        transactionRepository.save(transaction2);
        sendingAccount.get().setBalance(sendingAccount.get().getBalance() - amount);
        receiverAccount.get().setBalance(receiverAccount.get().getBalance() + amount);
        accountRepository.save(sendingAccount.get());
        accountRepository.save(receiverAccount.get());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }




}
