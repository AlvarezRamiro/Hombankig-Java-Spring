package com.mindhub.demo.controllers;


import com.mindhub.demo.dtos.LoanApplicationDTO;
import com.mindhub.demo.dtos.LoanDTO;
import com.mindhub.demo.models.*;
import com.mindhub.demo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class
LoanController {

    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientLoanRepository clientLoanRepository;


    //--  Lista de los préstamos  --
    @GetMapping("/loans")
    public List<LoanDTO> getLoan() {
        return loanRepository.findAll().stream().map(loan -> new LoanDTO(loan)).collect(toList());
    }


    //--  Devuelve Loan por parametro {id}  --
    @GetMapping("/loan/{id}")
    public LoanDTO getLoan(@PathVariable Long id) {
        return loanRepository.findById(id).map(LoanDTO::new).orElse(null);
    }


    //--  Post de requerimiento de un préstamo  --
    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> createClientLoan(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication) {


        // Valida si los campos no estén vacíos o nulos.
        if (loanApplicationDTO.getAmount() <= 0  || loanApplicationDTO.getPayments() <= 0 || loanApplicationDTO.getToAccountNumber().isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        Optional<Loan> loanFound = loanRepository.findById(loanApplicationDTO.getLoanId());
        Optional<Account> accountFound = accountRepository.findByNumber(loanApplicationDTO.getToAccountNumber());

        // Valida si el préstamo existe <isEmpty>.
        if(loanFound.isEmpty()){
            return new ResponseEntity<>("No existe el prestamo", HttpStatus.FORBIDDEN);
        }

        // Valida si el monto se excede.
        if (loanApplicationDTO.getAmount() > loanFound.get().getMaxAmount()) {
            return new ResponseEntity<>("Monto excedido", HttpStatus.FORBIDDEN);
        }

        // Valida si las cuotas ingresadas corresponden para ese prestamo.
        if (!loanFound.get().getPayments().contains(loanApplicationDTO.getPayments())){
            return new ResponseEntity<>("La cantidad de cuotas no es correcta", HttpStatus.FORBIDDEN);
        }

        // Valida si el monto se excede.
        if(accountFound.isEmpty()){
            return new ResponseEntity<>("No existe la cuenta", HttpStatus.FORBIDDEN);
        }

        // Valida si la cuenta pertenece al usuario actual.
        if (!clientRepository.findByEmail(authentication.getName()).get().getAccounts().contains(accountFound.get())) {
            return new ResponseEntity<>("La cuenta no te pertenece", HttpStatus.FORBIDDEN);
        }
        double amountTotal = loanApplicationDTO.getAmount() * 1.20;

        //Crea el prestamo con los datos ingresados, anteriormente validados.
        ClientLoan loanRequested = new ClientLoan(loanApplicationDTO.getPayments(),amountTotal,clientRepository.findByEmail(authentication.getName()).get(),loanFound.get());
        clientLoanRepository.save(loanRequested);
        transactionRepository.save(new Transaction(TransactionType.CREDIT, loanApplicationDTO.getAmount(),"loan approved", LocalDateTime.now(),accountFound.get()));
        accountFound.get().setBalance(accountFound.get().getBalance()+loanApplicationDTO.getAmount());
        accountRepository.save(accountFound.get());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

