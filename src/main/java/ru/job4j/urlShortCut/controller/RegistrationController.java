package ru.job4j.urlshortcut.controller;


import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.urlshortcut.dto.SiteRegisterDto;
import ru.job4j.urlshortcut.dto.SiteRegisterErrorDto;
import ru.job4j.urlshortcut.dto.SiteRegisterRequestDto;
import ru.job4j.urlshortcut.mapper.MapperDto;
import ru.job4j.urlshortcut.model.Credential;
import ru.job4j.urlshortcut.model.Site;
import ru.job4j.urlshortcut.service.CredentialService;
import ru.job4j.urlshortcut.service.RegistrationService;
import ru.job4j.urlshortcut.service.SiteService;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/")
@AllArgsConstructor
@Log4j
public class RegistrationController {

    private final SiteService siteService;
    private final MapperDto mapper;
    private final RegistrationService registrationService;
    private final CredentialService credentialService;

    @PostMapping("register")
    public ResponseEntity<?> register(@Valid @RequestBody SiteRegisterRequestDto requestDto) {
        String name = requestDto.getSite();
        Optional<Site> optionalSite = siteService.findByName(name);
        if (optionalSite.isPresent()) {
            SiteRegisterErrorDto errorSiteDto = new SiteRegisterErrorDto();
            errorSiteDto.setMessage(
                    String.format("Registration failed as the site with domain name '%s' already exists", name
                    ));
            log.error(String.format("Registration failed '%s' already exists", name
            ));
            return ResponseEntity.badRequest().body(errorSiteDto);
        }
        Credential credentialCreated = credentialService.createCredential();
        String login = credentialCreated.getLogin();
        String password = credentialCreated.getPassword();
        Credential credential = credentialService.save(credentialCreated);
        Optional<Site> siteRegistered = registrationService.register(name, credential);
        SiteRegisterDto registerSiteDto = mapper.mapToRegisterDto(siteRegistered.get(), login, password);
        return ResponseEntity.status(HttpStatus.CREATED).body(registerSiteDto);
    }

}
