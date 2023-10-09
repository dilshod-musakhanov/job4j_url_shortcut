package ru.job4j.urlshortcut.util;

import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public final class UrlParseUtil {

    private final String patternString = "(https?://([^/]+)/)";
    private final Pattern pattern = Pattern.compile(patternString);

    private UrlParseUtil() {

    }

    public Optional<String> extractSiteName(String urlAddress) {
        Matcher matcher = pattern.matcher(urlAddress);
        if (matcher.find()) {
            String name = matcher.group(2);
            return Optional.of(name);
        }
        return Optional.empty();
    }
}
