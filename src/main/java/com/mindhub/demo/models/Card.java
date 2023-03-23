package com.mindhub.demo.models;
import org.hibernate.annotations.GenericGenerator;
import utils.CardUtils;
import javax.persistence.*;
import java.time.LocalDate;


@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    private String cardHolder;

    private CardType type;

    private cardColor color;

    private String number;

    private String cvv;

    private LocalDate thruDate;

    private LocalDate fromDate;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Client_id")
    private Client client;

    public Card(Client client, CardType type, cardColor color) {
        this.client = client;
        this.cardHolder = client.getFirstName() + " " + client.getLastName();
        this.type = type;
        this.color = color;
        this.number = CardUtils.generateRandomNumbers(4)+"-"+CardUtils.generateRandomNumbers(4)+"-"+CardUtils.generateRandomNumbers(4)+"-"+CardUtils.generateRandomNumbers(4);
        this.cvv = CardUtils.generateRandomNumbers(3);
        this.fromDate = LocalDate.now();
        this.thruDate = fromDate.plusYears(5);
    }

    public Card() {
    }

//    public String generateCvv() {
//        String newCvv = "";
//        for(int i = 0; i < 3; i++) {
//            int newNumber = (int) (Math.random() * 10);
//            newCvv += String.valueOf(newNumber);
//        }
//        return  newCvv;
//    }
//    public String generateNumber() {
//        String cardNumber = "";
//        for(int i = 0; i < 4; ++i) {
//            int newNumber = (int) (Math.random() * 10);
//            cardNumber += String.valueOf(newNumber);
//        }
//        return cardNumber;
//    }


    public Long getId() {
        return id;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public cardColor getColor() {
        return color;
    }

    public void setColor(cardColor color) {
        this.color = color;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }


}
