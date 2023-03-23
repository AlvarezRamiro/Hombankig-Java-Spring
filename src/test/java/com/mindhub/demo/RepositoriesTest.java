package com.mindhub.demo;


import com.mindhub.demo.models.Card;
import com.mindhub.demo.models.Client;
import com.mindhub.demo.models.Loan;
import com.mindhub.demo.repositories.CardRepository;
import com.mindhub.demo.repositories.ClientRepository;
import com.mindhub.demo.repositories.LoanRepository;
import com.mindhub.demo.repositories.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class RepositoriesTest {

        @Autowired
        LoanRepository loanRepository;

        @Autowired
        ClientRepository clientRepository;

        @Autowired
        TransactionRepository transactionRepository;

        @Autowired
        CardRepository cardRepository;



        @Test
        public void existLoans(){
            List<Loan> loans = loanRepository.findAll();
            assertThat(loans,is(not(empty())));
        }

        @Test
        public void existPersonalLoan(){
            List<Loan> loans = loanRepository.findAll();
            assertThat(loans, hasItem(hasProperty("name", is("Personal"))));
        }



            @Test
            public void FromDateIsTodayOrPriorToToday() {
                List<Card> cards = cardRepository.findAll();
                assertThat(cards, everyItem(hasProperty("fromDate", lessThanOrEqualTo(LocalDate.now()))));
            }

         @Test
        public void cardNumberLength(){
        List<Card> cards = cardRepository.findAll();
        for(Card cs: cards){
            assertThat(cs.getNumber().length(), comparesEqualTo(19));
        }
    }

    }

