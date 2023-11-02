package ru.job4j.urlshortcut.util.generator;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public final class CodeGenerator {
    private final int length = 6;
    private final String smallChars = "abcdefghijklmnopqrstuvwxyz";
    private final String numbers = "0123456789";

    private CodeGenerator() {

    }

    public String generateCode() {
        String values = smallChars + numbers;
        Random random = new Random();
        char[] converted = new char[length];
        for (int i = 0; i < length; i++) {
            converted[i] = values.charAt(random.nextInt(values.length()));
        }
        return new String(converted);
    }
}
