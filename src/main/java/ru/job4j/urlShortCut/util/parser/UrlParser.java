package ru.job4j.urlshortcut.util.parser;

import org.springframework.stereotype.Component;


@Component
public final class UrlParser {

    private UrlParser() {
    }

    public String extractSiteName(String url) {
        String protocol = url.split(":")[0];
        String resource = url.substring(protocol.length() + 3);
        return resource.split("/")[0];
    }
}
