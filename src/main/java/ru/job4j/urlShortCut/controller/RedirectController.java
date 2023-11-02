package ru.job4j.urlshortcut.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.urlshortcut.dto.UrlCodeDto;
import ru.job4j.urlshortcut.model.Statistic;
import ru.job4j.urlshortcut.service.StatisticService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class RedirectController {

    private final StatisticService statisticService;

    @PostMapping("redirect")
    public ResponseEntity<?> redirect(@Validated @RequestBody UrlCodeDto dto, HttpServletResponse response) {
        String code = dto.getCode();
        Statistic statistic = statisticService.findAndIncrease(code);
        String urlName = statistic.getUrl().getName();
        response.setHeader("HTTP CODE - 302 REDIRECT URL", urlName);
        response.setStatus(HttpStatus.FOUND.value());
        return ResponseEntity.status(HttpStatus.FOUND).body("Redirecting to: " + urlName);
    }

}
