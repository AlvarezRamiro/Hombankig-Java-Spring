package com.mindhub.demo;

import com.mindhub.demo.models.*;
import com.mindhub.demo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

import static com.mindhub.demo.models.cardColor.GOLD;
import static com.mindhub.demo.models.cardColor.TITANIUM;
import static com.mindhub.demo.models.TransactionType.CREDIT;
import static com.mindhub.demo.models.TransactionType.DEBIT;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

/*
	@Autowired
	PasswordEncoder passwordEncoder;


	@Bean
	public CommandLineRunner initData(ClientRepository clientrepository,
									  AccountRepository accountrepository,
									  TransactionRepository transactionRepository,
									  LoanRepository loanRepository,
									  ClientLoanRepository clientloanRepository,
									  CardRepository cardRepository) {
		return (args) -> {

			Client client1 = new Client("Melba", "Alvarez", "Melba@gmail.com",passwordEncoder.encode("123"));
			Client client2 = new Client("Juan", "Gomez", "Juan@gmail.com",passwordEncoder.encode("456"));
			Client client3 = new Client("Camila", "Perez", "Camila@gmail.com",passwordEncoder.encode("789"));
			Client client4 = new Client("Melba", "Gonzalez", "Melgon@gmail.com",passwordEncoder.encode("312"));

			clientrepository.save(client1);
			clientrepository.save(client2);
			clientrepository.save(client3);
			clientrepository.save(client4);

			Account account = new Account( LocalDateTime.now(), 5000, client1);
			accountrepository.save(account);
			Account account2 = new Account(LocalDateTime.now().plusDays(1), 7500, client1);
			accountrepository.save(account2);
			Account account3 = new Account( LocalDateTime.now(), 100, client3);
			accountrepository.save(account3);

			Transaction transaction = new Transaction(CREDIT,200,"cuenta corriente",LocalDateTime.now(),account);
			transactionRepository.save(transaction);

			Transaction transaction1 = new Transaction(CREDIT,200,"cuenta corriente",LocalDateTime.now().minusDays(5),account);
			transactionRepository.save(transaction1);

			Transaction transaction2 = new Transaction(DEBIT,600,"Pago productos",LocalDateTime.now(),account);
			transactionRepository.save(transaction2);

			Transaction transaction3 = new Transaction(DEBIT,1200,"Pago productos",LocalDateTime.now(),account2);
			transactionRepository.save(transaction3);

			Transaction transaction4 = new Transaction(CREDIT,4000,"Sueldo",LocalDateTime.now().plusDays(4),account2);
			transactionRepository.save(transaction4);

			Transaction transaction5 = new Transaction(CREDIT,50,"PAGO",LocalDateTime.now().plusDays(4),account3);
			transactionRepository.save(transaction5);
			Transaction transaction6 = new Transaction(DEBIT,25,"Sueldo",LocalDateTime.now().plusDays(4),account3);
			transactionRepository.save(transaction6);

			Loan loan1 = new Loan("Hipotecario",20000, List.of(6, 12, 24));
			loanRepository.save(loan1);

			Loan loan2 = new Loan("Personal",100000, List.of(12, 24));
			loanRepository.save(loan2);

			ClientLoan clientLoan1 = new ClientLoan(6,50000,client1,loan1);
			clientloanRepository.save(clientLoan1);

			ClientLoan clientLoan2 = new ClientLoan(6,30000,client2,loan2);
			clientloanRepository.save(clientLoan2);

			ClientLoan clientLoan3 = new ClientLoan(24,90000,client1,loan1);
			clientloanRepository.save(clientLoan3);

			Card card1 = new Card(client1, CardType.DEBIT ,GOLD);
			cardRepository.save(card1);

			Card card2 = new Card(client1, CardType.CREDIT ,TITANIUM);
			cardRepository.save(card2);

		};
	}
*/

}


