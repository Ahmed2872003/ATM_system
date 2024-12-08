package atmsystem.utils;

import java.util.Random;

public class CardNumber {
    
    final private static int bound = 10;

    public static String generate(int length) {
        StringBuilder cardNumber = new StringBuilder();

        Random random = new Random();
        
        for (int i = 0; i < length; i++) {
            cardNumber.append(random.nextInt(bound));
        }
        
        return cardNumber.toString();
    }
}
