package ru.job4j.urlshortcut.service;

import ru.job4j.urlshortcut.model.Statistic;

import java.util.List;
import java.util.Optional;

public interface StatisticService {

    Statistic save(Statistic statistic);
    Optional<Statistic> findByUrlId(int urlId);
    void increaseVisits(int statisticId);
    Statistic findAndIncrease(String code);
    List<Statistic> getStatistic(String domainName);
}
