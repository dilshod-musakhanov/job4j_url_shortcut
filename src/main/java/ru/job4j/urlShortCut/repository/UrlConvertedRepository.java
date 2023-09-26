package ru.job4j.urlshortcut.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.urlshortcut.model.UrlConverted;

import java.util.List;
import java.util.Optional;

public interface UrlConvertedRepository extends CrudRepository<UrlConverted, Integer> {
    List<UrlConverted> findAll();

    Optional<UrlConverted> findByUrlShortcut(String urlShortcut);

    Optional<UrlConverted> findByUrl(String url);
}
