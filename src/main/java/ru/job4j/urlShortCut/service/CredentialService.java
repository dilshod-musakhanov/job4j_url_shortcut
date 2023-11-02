package ru.job4j.urlshortcut.service;

import ru.job4j.urlshortcut.model.Credential;

public interface CredentialService {

    Credential createCredential();
    Credential save(Credential credential);
    boolean findByLogin(String login);
    boolean findByPassword(String password);
}
