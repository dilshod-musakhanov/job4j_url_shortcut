package ru.job4j.urlshortcut.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.job4j.urlshortcut.model.Statistic;

import java.util.List;
import java.util.Optional;

public interface StatisticRepository extends CrudRepository<Statistic, Integer> {
    Optional<Statistic> findById(int id);
    List<Statistic> findAll();

    @Query(value = "SELECT s FROM Statistic s WHERE s.url.id = :urlId")
    Optional<Statistic> findByUrlId(@Param("urlId") int urlId);

    @Query("FROM Statistic WHERE url.site.name = :domainName")
    List<Statistic> getStatistic(@Param("domainName") String domainName);

    @Modifying
    @Query(value = "UPDATE Statistic s SET s.visits = s.visits + 1 WHERE s.id = :statisticId")
    void increaseVisits(@Param("statisticId") int statisticId);

}
