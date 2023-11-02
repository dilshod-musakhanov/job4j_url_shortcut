package ru.job4j.urlshortcut.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import ru.job4j.urlshortcut.model.Site;
import ru.job4j.urlshortcut.model.Statistic;
import ru.job4j.urlshortcut.model.Url;
import ru.job4j.urlshortcut.repository.SiteRepository;
import ru.job4j.urlshortcut.repository.StatisticRepository;
import ru.job4j.urlshortcut.repository.UrlRepository;
import ru.job4j.urlshortcut.util.generator.CodeGenerator;
import ru.job4j.urlshortcut.util.parser.UrlParser;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
@Log4j
public class UrlServiceImpl implements UrlService {

    private final SiteRepository siteRepository;
    private final UrlRepository urlRepository;
    private final StatisticRepository statisticRepository;
    private final CodeGenerator codeGenerator;
    private final UrlParser urlParser;

    @Override
    public String save(String name) {
        Site site = getSite(name);
        Optional<Url> resUrl = urlRepository.findByName(name);
        if (resUrl.isPresent()) {
            return resUrl.get().getCode();
        }
        String code = generateCode();
        Url newUrl = new Url();
        newUrl.setName(name);
        newUrl.setSite(site);
        newUrl.setCode(code);
        Url savedUrl = urlRepository.save(newUrl);
        Statistic newStatistic = new Statistic();
        newStatistic.setUrl(savedUrl);
        newStatistic.setVisits(0);
        statisticRepository.save(newStatistic);
        return savedUrl.getCode();
    }

    @Override
    public Url findByCode(String code) {
        Optional<Url> optionalUrl = urlRepository.findByCode(code);
        return optionalUrl.get();
    }

    @Override
    public boolean isCodeExist(String code) {
        Optional<Url> optionalUrl = urlRepository.findByCode(code);
        return optionalUrl.isPresent();
    }


    private String generateCode() {
        String code = codeGenerator.generateCode();
        while (isCodeExist(code)) {
            code = codeGenerator.generateCode();
        }
        return code;
    }

    private Site getSite(String url) {
        String name = urlParser.extractSiteName(url);
        Optional<Site> site = siteRepository.findByName(name);
        if (site.isEmpty()) {
            log.error(String.format("Site with this '%s' is not found. Please register site domain first", url));
            throw new NoSuchElementException("Site is not found. Please register site domain first");
        }
        return site.get();
    }

}
