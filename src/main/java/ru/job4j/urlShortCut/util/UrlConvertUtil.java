package ru.job4j.urlshortcut.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public final class UrlConvertUtil {
    private static final int LENGTH = 6;
    private static final String SMALL_CHARS = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS = "0123456789";

    private UrlConvertUtil() {

    }

    public static String convertUrl() {
        String values = SMALL_CHARS + NUMBERS;
        Random random = new Random();
        char[] converted = new char[LENGTH];
        for (int i = 0; i < LENGTH; i++) {
            converted[i] = values.charAt(random.nextInt(values.length()));
        }
        return new String(converted);
    }

}
