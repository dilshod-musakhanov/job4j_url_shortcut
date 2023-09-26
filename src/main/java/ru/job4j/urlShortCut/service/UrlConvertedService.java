package ru.job4j.urlshortcut.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.urlshortcut.dto.UrlConvertedDto;
import ru.job4j.urlshortcut.model.UrlConverted;
import ru.job4j.urlshortcut.repository.UrlConvertedRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UrlConvertedService {
    private final UrlConvertedRepository urlConvertedRepository;

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
}
