package ru.job4j.urlshortcut.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.urlshortcut.dto.UrlConvertedDto;
import ru.job4j.urlshortcut.model.Site;
import ru.job4j.urlshortcut.model.UrlConverted;
import ru.job4j.urlshortcut.repository.SiteRepository;
import ru.job4j.urlshortcut.repository.UrlConvertedRepository;
import ru.job4j.urlshortcut.util.UrlConvertUtil;
import ru.job4j.urlshortcut.util.UrlParseUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UrlConvertedService {
    private final UrlConvertedRepository urlConvertedRepository;
    private final SiteRepository siteRepository;
    private final UrlConvertUtil urlConvertUtil;
    private final UrlParseUtil urlParseUtil;

    public UrlConverted save(UrlConverted urlConverted) {
        return urlConvertedRepository.save(urlConverted);
    }

    public Optional<UrlConverted> findByUrlShortcut(String urlShortcut) {
        return urlConvertedRepository.findByUrlShortcut(urlShortcut);
    }

    public Optional<UrlConverted> findByUrl(String url) {
        return urlConvertedRepository.findByUrl(url);
    }

    public List<UrlConvertedDto> findAll() {
        List<UrlConverted> list =  urlConvertedRepository.findAll();
        List<UrlConvertedDto> statisticList = list.stream()
                .map(urlConverted -> new UrlConvertedDto(urlConverted.getUrl(), urlConverted.getVisits()))
                .collect(Collectors.toList());
        return statisticList;
    }

    public UrlConverted createAndSaveUrlConverted(String url, Site site) {
        UrlConverted urlConverted = new UrlConverted();
        urlConverted.setUrl(url);
        urlConverted.setUrlShortcut(urlConvertUtil.convertUrl());
        urlConverted.setVisits(0);
        urlConverted.setSiteId(site.getId());
        return urlConvertedRepository.save(urlConverted);
    }

    public Map<Optional<UrlConverted>, String> getOrCreateUrlShortcut(String urlAddress) {
        var siteName = urlParseUtil.extractSiteName(urlAddress);
        if (siteName.isEmpty()) {
            throw new IllegalArgumentException("Invalid URL format. Url has to start with http://");
        }
        var name = siteName.get();
        var siteOptional = siteRepository.findByName(name);
        if (siteOptional.isEmpty()) {
            throw new IllegalArgumentException("Site name is not valid");
        }
        Map<Optional<UrlConverted>, String> resultMap = new HashMap<>();
        Optional<UrlConverted> urlConvertedOptional = findByUrl(urlAddress);
        resultMap.put(urlConvertedOptional, name);
        return resultMap;
    }

    @Transactional
    public void increaseVisits(int urlConvertedId) {
        urlConvertedRepository.increaseVisits(urlConvertedId);
    }
}
