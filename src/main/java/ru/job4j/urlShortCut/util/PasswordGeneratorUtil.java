package ru.job4j.urlshortcut.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public final class PasswordGeneratorUtil {
    private final int length = 8;
    private final String smallChars = "abcdefghijklmnopqrstuvwxyz";
    private final String numbers = "0123456789";
    private final String symbols = "!@#$&*?%";

    private PasswordGeneratorUtil() {

    }

    public String generatePassword() {
        String values = smallChars + symbols + numbers;
        Random random = new Random();
        char[] password = new char[length];
        for (int i = 0; i < length; i++) {
            password[i] = values.charAt(random.nextInt(values.length()));
        }
        return new String(password);
    }
}
