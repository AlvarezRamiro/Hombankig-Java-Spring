package utils;

import com.mindhub.demo.dtos.AccountDTO;
import com.mindhub.demo.dtos.TransactionDTO;
import com.mindhub.demo.models.Account;
import com.mindhub.demo.models.Transaction;
import com.mindhub.demo.models.TransactionType;

import java.util.Random;

public final class AccountUtils {

    private AccountUtils() {
    }

//      public static double getBalance(Account account) {
//        double total= account.getBalance();
//        for(Transaction t : account.getTransactions()){
//            switch (t.getType()){
//                case DEBIT : total = total + t.getAmount();
//                    break;
//                case CREDIT : total = total + t.getAmount();
//                    break;
//            }
//        }
//        return total;
//    }

    public static int generateRandomNumbersMinMax(int min, int max) {
        Random random = new Random();
        int randomNumber = random.nextInt(max - min) + min;
        return randomNumber;
    }

}
