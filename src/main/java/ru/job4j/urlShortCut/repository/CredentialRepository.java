package ru.job4j.urlshortcut.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.urlshortcut.model.Credential;

import java.util.Optional;

public interface CredentialRepository extends CrudRepository<Credential, Integer> {
    Optional<Credential> findByLogin(String login);
    Optional<Credential> findByPassword(String password);
}
