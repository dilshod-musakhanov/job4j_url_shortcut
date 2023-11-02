package ru.job4j.urlshortcut.util.generator;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public final class LoginGenerator {
    private final int length = 7;
    private final String capitalChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String numbers = "0123456789";

    private LoginGenerator() {

    }

    public String generateLogin() {
        String values = capitalChars + numbers;
        Random random = new Random();
        char[] login = new char[length];
        for (int i = 0; i < length; i++) {
            login[i] = values.charAt(random.nextInt(values.length()));
        }
        return new String(login);
    }
}
