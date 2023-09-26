package ru.job4j.urlshortcut.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
public class SiteController {
    private final SiteService siteService;
    private final UrlConvertedService urlConvertedService;
    private final ObjectMapper objectMapper;

    private SiteController(SiteService siteService, UrlConvertedService urlConvertedService, ObjectMapper objectMapper) {
        this.siteService = siteService;
        this.urlConvertedService = urlConvertedService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/registration")
    public UrlRegisteredDto register(@RequestBody Map<String, String> site) {
        String name = site.get("site");
        Optional<Site> siteOptional = siteService.findByName(name);
        if (siteOptional.isPresent()) {
            throw new IllegalArgumentException("The site already exists. Use your login and password");
        }
        Site siteNew = siteService.save(name);
        var urd = new UrlRegisteredDto();
        urd.setRegistration("true");
        urd.setLogin(siteNew.getLogin());
        urd.setPassword(siteNew.getPassword());
        return urd;
    }

    @PostMapping("/convert")
    public ResponseEntity<Map<String, String>> getCode(@RequestBody Map<String, String> url) {
        var urlAddress = url.get("url");
        var siteName = UrlParseUtil.extractSiteName(urlAddress);
        if (siteName.isEmpty()) {
            throw new IllegalArgumentException("Invalid URL format. Url has to start with http://");
        }
        var siteOptional = siteService.findByName(siteName.get());
        if (siteOptional.isEmpty()) {
            throw new IllegalArgumentException("Site name is not valid");
        }
        var urlAddressOptional = urlConvertedService.findByUrl(urlAddress);
        if (urlAddressOptional.isPresent()) {
            return ResponseEntity.ok(Map.of("code", urlAddressOptional.get().getUrlShortcut()));
        }
        return siteService.findByName(siteName.get())
                .map(site -> {
                    var urlConverted = new UrlConverted();
                    urlConverted.setUrl(urlAddress);
                    urlConverted.setUrlShortcut(UrlConvertUtil.convertUrl());
                    urlConverted.setVisits(0);
                    urlConverted.setSiteId(site.getId());
                    urlConvertedService.save(urlConverted);
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
        var url = urlConverted.getUrl();
        urlConverted.setVisits(urlConverted.getVisits() + 1);
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

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public void exceptionHandlerIllegalArgumentException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() {{
            put("message", e.getMessage());
            put("type", e.getClass());
        }}));
    }

}
