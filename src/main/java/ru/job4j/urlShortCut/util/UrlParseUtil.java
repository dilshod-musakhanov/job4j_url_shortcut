package ru.job4j.urlshortcut.util;

import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public final class UrlParseUtil {

    private static final String PATTERN = "https://([^/]+)/";

    private UrlParseUtil() {

    }

    public static Optional<String> extractSiteName(String urlAdress) {
        Pattern regex = Pattern.compile(PATTERN);
        Matcher matcher = regex.matcher(urlAdress);
        if (matcher.find()) {
            String name = matcher.group(1);
            return Optional.of(name);
        }
        return Optional.empty();
    }
}
