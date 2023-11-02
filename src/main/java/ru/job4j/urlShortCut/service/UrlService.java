package ru.job4j.urlshortcut.service;

import ru.job4j.urlshortcut.model.Url;

public interface UrlService {

    String save(String name);
    Url findByCode(String code);
    boolean isCodeExist(String code);
}
