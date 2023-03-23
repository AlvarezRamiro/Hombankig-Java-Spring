package com.mindhub.demo.controllers;


import com.mindhub.demo.dtos.CardDTO;
import com.mindhub.demo.models.Card;
import com.mindhub.demo.models.CardType;
import com.mindhub.demo.models.Client;
import com.mindhub.demo.models.cardColor;
import com.mindhub.demo.repositories.CardRepository;
import com.mindhub.demo.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ClientRepository clientRepository;


    //Devuelve el cliente por parametro de la card{id}.

    @GetMapping("/clients/{id}/cards")
    public List<CardDTO> getCard(@PathVariable Long id){
        Optional<Card> cardOptional = cardRepository.findById(id);
        return cardRepository.findAll().stream().map(CardDTO::new).collect(toList());
    }

    @DeleteMapping("/card/delete/{id}")
    public ResponseEntity<Object> deleteCard(@PathVariable Long id){
        cardRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Nueva Card para el usuario actual.
    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCard(@RequestParam CardType cardType,
                                             @RequestParam cardColor cardColor,
                                             Authentication authentication) {

        //Valida el cliente actual.
        Optional<Client> client = this.clientRepository.findByEmail(authentication.getName());
        if (client.isPresent()) {
            //Valida que el usuario no tenga 3 cards del mismo tipo (CREDIT, DEBIT).
            if (client.get().getCards().stream().filter(card -> card.getType()==cardType).count()>=3){
                return new ResponseEntity<>("You already have 3 "+ cardType + " cards ", HttpStatus.FORBIDDEN);
            }
            //Crea la card.
        cardRepository.save(new Card(client.get(), cardType, cardColor));
            return new ResponseEntity<>(HttpStatus.CREATED);

            //Error
        }else {
            return new ResponseEntity<>("Missing client", HttpStatus.FORBIDDEN);
        }

    }

}
