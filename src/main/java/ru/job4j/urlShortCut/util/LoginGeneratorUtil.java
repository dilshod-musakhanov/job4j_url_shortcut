package ru.job4j.urlshortcut.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public final class LoginGeneratorUtil {

    private static final int LENGTH = 7;
    private static final String CAPITAL_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBERS = "0123456789";

    private LoginGeneratorUtil() {

    }

    public static String generateLogin() {
        String values = CAPITAL_CHARS + NUMBERS;
        Random random = new Random();
        char[] login = new char[LENGTH];
        for (int i = 0; i < LENGTH; i++) {
            login[i] = values.charAt(random.nextInt(values.length()));
        }
        return new String(login);
    }
}
