package utils;

public final class CardUtils {

    private CardUtils() {
    }


    public static String generateRandomNumbers(int quantity) {
        String cardNumber = "";
        for(int i = 0; i < quantity; ++i) {
            int newNumber = (int) (Math.random() * 10);
            cardNumber += String.valueOf(newNumber);
        }
        return cardNumber;
    }

}
