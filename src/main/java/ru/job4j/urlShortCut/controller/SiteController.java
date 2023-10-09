package ru.job4j.urlshortcut.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.urlshortcut.dto.UrlConvertedDto;
import ru.job4j.urlshortcut.dto.UrlRegisteredDto;
import ru.job4j.urlshortcut.model.UrlConverted;
import ru.job4j.urlshortcut.model.Site;
import ru.job4j.urlshortcut.service.SiteService;
import ru.job4j.urlshortcut.service.UrlConvertedService;
import ru.job4j.urlshortcut.util.UrlConvertUtil;
import ru.job4j.urlshortcut.util.UrlParseUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/sites")
@AllArgsConstructor
public class SiteController {
    private final SiteService siteService;
    private final UrlConvertedService urlConvertedService;
    private final ModelMapper modelMapper;
    private final UrlParseUtil parseUtil;

    @PostMapping("/registration")
    public ResponseEntity<?> register(@RequestBody Map<String, String> site) {
        String name = site.get("site");
        Optional<Site> siteOptional = siteService.findByName(name);
        if (siteOptional.isPresent()) {
            return ResponseEntity.badRequest().body(new UrlRegisteredDto());
        }
        Site siteNew = siteService.save(name);
        var urd = modelMapper.map(siteNew, UrlRegisteredDto.class);
        urd.setRegistration(true);
        return ResponseEntity.status(HttpStatus.CREATED).body(urd);
    }

    @PostMapping("/convert")
    public ResponseEntity<Map<String, String>> getCode(@RequestBody Map<String, String> url) {
        var urlAddress = url.get("url");
        var resultMap = urlConvertedService.getOrCreateUrlShortcut(urlAddress);
        Optional<UrlConverted> urlConvertedOptional = resultMap.keySet().iterator().next();
        var name = resultMap.get(urlConvertedOptional);
        if (urlConvertedOptional.isPresent()) {
            return ResponseEntity.ok(Map.of("code", urlConvertedOptional.get().getUrlShortcut()));
        }
        return siteService.findByName(name)
                .map(site -> {
                    var urlConverted = urlConvertedService.createAndSaveUrlConverted(urlAddress, site);
                    return ResponseEntity.ok(Map.of("code", urlConverted.getUrlShortcut()));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/redirect")
    public ResponseEntity<?> redirectToUrl(@RequestBody Map<String, String> code, HttpServletResponse response) {
        String result = code.get("code");
        var urlConvertedOptional = urlConvertedService.findByUrlShortcut(result);
        if (urlConvertedOptional.isEmpty()) {
            throw new IllegalArgumentException("Code is not valid");
        }
        var urlConverted = urlConvertedOptional.get();
        var id = urlConverted.getId();
        var url = urlConverted.getUrl();
        urlConvertedService.increaseVisits(id);
        urlConvertedService.save(urlConverted);
        response.setHeader("Location", url);
        response.setStatus(HttpStatus.FOUND.value());
        return ResponseEntity.status(HttpStatus.FOUND).body("Redirecting to: " + url);
    }

    @GetMapping("/statistic")
    public ResponseEntity<List<UrlConvertedDto>> getStatistic() {
        List<UrlConvertedDto> list = urlConvertedService.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
