package ru.job4j.urlshortcut.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.urlshortcut.model.Site;
import ru.job4j.urlshortcut.repository.SiteRepository;
import ru.job4j.urlshortcut.util.LoginGeneratorUtil;
import ru.job4j.urlshortcut.util.PasswordGeneratorUtil;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SiteService {
    private final SiteRepository siteRepository;

    public Site save(String name) {
        Site site = new Site();
        site.setName(name);
        site.setLogin(LoginGeneratorUtil.generateLogin());
        site.setPassword(PasswordGeneratorUtil.generatePassword());
        return siteRepository.save(site);
    }

    public Optional<Site> findById(int id) {
        return siteRepository.findById(id);

    }
    public Optional<Site> findByName(String name) {
        return siteRepository.findByName(name);
    }

    public List<Site> findAll() {
        return siteRepository.findAll();
    }


}
