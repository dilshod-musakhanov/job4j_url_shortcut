package ru.job4j.urlshortcut.service;

import ru.job4j.urlshortcut.model.Credential;
import ru.job4j.urlshortcut.model.Site;

import java.util.Optional;

public interface RegistrationService {

    Optional<Site> register(String name, Credential credential);
}
