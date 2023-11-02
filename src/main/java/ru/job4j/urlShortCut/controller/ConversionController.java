package ru.job4j.urlshortcut.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.urlshortcut.dto.UrlConvertRequestDto;
import ru.job4j.urlshortcut.service.StatisticService;
import ru.job4j.urlshortcut.service.UrlService;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class ConversionController {

    private final UrlService urlService;

    @PostMapping("convert")
    public ResponseEntity<?> convert(@Valid @RequestBody UrlConvertRequestDto requestDto) {
        String name = requestDto.getUrl();
        String code = urlService.save(name);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("code", code));
    }

}
