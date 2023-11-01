package ru.job4j.urlshortcut.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.urlshortcut.model.Url;

import java.util.Optional;

public interface UrlRepository extends CrudRepository<Url, Integer> {
    Optional<Url> findByName(String name);
    Optional<Url> findByCode(String code);
}
