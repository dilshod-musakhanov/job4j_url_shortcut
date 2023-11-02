package ru.job4j.urlshortcut.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.urlshortcut.model.Credential;
import ru.job4j.urlshortcut.model.Site;
import ru.job4j.urlshortcut.repository.SiteRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final SiteRepository siteRepository;

    @Override
    public Optional<Site> register(String name, Credential credential) {
        Site site = siteRepository.save(
                new Site(name, true, credential)
        );
        return Optional.of(site);
    }
}
