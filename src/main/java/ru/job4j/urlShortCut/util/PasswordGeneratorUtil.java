package ru.job4j.urlshortcut.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public final class PasswordGeneratorUtil {
    private static final int LENGTH = 8;
    private static final String SMALL_CHARS = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS = "0123456789";
    private static final String SYMBOLS = "!@#$&*?%";

    private PasswordGeneratorUtil() {

    }

    public static String generatePassword() {
        String values = SMALL_CHARS + SYMBOLS + NUMBERS;
        Random random = new Random();
        char[] password = new char[LENGTH];
        for (int i = 0; i < LENGTH; i++) {
            password[i] = values.charAt(random.nextInt(values.length()));
        }
        return new String(password);
    }
}
