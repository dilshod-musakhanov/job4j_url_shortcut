package ru.job4j.urlshortcut.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.job4j.urlshortcut.model.UrlConverted;

import java.util.List;
import java.util.Optional;

public interface UrlConvertedRepository extends CrudRepository<UrlConverted, Integer> {
    List<UrlConverted> findAll();

    Optional<UrlConverted> findByUrlShortcut(String urlShortcut);

    Optional<UrlConverted> findByUrl(String url);

    @Modifying
    @Query(value = "update url_converted set visits = visits + 1 where id = :urlConvertedId",
            nativeQuery = true)
    void increaseVisits(@Param("urlConvertedId") int urlConvertedId);

}
