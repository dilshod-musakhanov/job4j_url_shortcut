package ru.job4j.urlshortcut.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.urlshortcut.dto.SiteStatisticRequestDto;
import ru.job4j.urlshortcut.mapper.MapperDto;
import ru.job4j.urlshortcut.model.Statistic;
import ru.job4j.urlshortcut.service.SiteService;
import ru.job4j.urlshortcut.service.StatisticService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class StatisticController {

    private final StatisticService statisticService;
    private final MapperDto mapperDto;

    @GetMapping("statistic")
    public ResponseEntity<?> getStatistic(@Validated @RequestBody SiteStatisticRequestDto dto) {
        String domainName = dto.getSite();
        List<Statistic> list = statisticService.getStatistic(domainName);
        return ResponseEntity.ok().body(list.stream().map(mapperDto::mapToStatisticDto)
                .collect(Collectors.toList()));
    }
}
