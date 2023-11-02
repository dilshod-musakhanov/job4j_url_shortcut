package ru.job4j.urlshortcut.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.urlshortcut.model.Statistic;
import ru.job4j.urlshortcut.model.Url;
import ru.job4j.urlshortcut.repository.StatisticRepository;
import ru.job4j.urlshortcut.repository.UrlRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
@Log4j
public class StatisticServiceImpl implements StatisticService {

    private final StatisticRepository statisticRepository;
    private final UrlRepository urlRepository;

    @Override
    public Statistic save(Statistic statistic) {
        return statisticRepository.save(statistic);
    }

    @Override
    public Optional<Statistic> findByUrlId(int urlId) {
        return statisticRepository.findByUrlId(urlId);
    }

    @Transactional
    @Override
    public void increaseVisits(int statisticId) {
        statisticRepository.increaseVisits(statisticId);
    }

    @Transactional
    @Override
    public Statistic findAndIncrease(String code) {
        Optional<Url> urlOptional = urlRepository.findByCode(code);
        if (urlOptional.isEmpty()) {
            log.error(String.format("Code '%s' is not valid", code));
            throw new NoSuchElementException(String.format("Code '%s' is not valid", code));
        }
        int id = urlOptional.get().getId();
        Optional<Statistic> optionalStatistic = findByUrlId(id);
        if (optionalStatistic.isEmpty()) {
            log.error(String.format("No statistic found by url '%s'", urlOptional.get().getName()));
            throw new NoSuchElementException(String.format("No statistic found by url '%s'", urlOptional.get().getName()));
        }
        Statistic statistic = optionalStatistic.get();
        int statId = statistic.getId();
        increaseVisits(statId);
        save(statistic);
        return statistic;
    }

    @Override
    public List<Statistic> getStatistic(String domainName) {
        List<Statistic> list = statisticRepository.getStatistic(domainName);
        if (list.isEmpty()) {
            log.error(String.format("No statistic found by domain '%s'", domainName));
            throw new NoSuchElementException(String.format("No statistic found by domain '%s'", domainName));
        }
        return list;
    }
}
