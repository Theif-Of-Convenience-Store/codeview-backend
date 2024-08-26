package codeview.main.util;

import java.security.SecureRandom;

public class KeyGenerator {
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static String generateKey() {
        StringBuilder key = new StringBuilder(20);
        for (int i = 0; i < 20; i++) {
            key.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return key.toString();
    }
}
