package ru.job4j.urlshortcut.mapper;

import org.springframework.stereotype.Component;
import ru.job4j.urlshortcut.dto.SiteRegisterDto;
import ru.job4j.urlshortcut.dto.SiteStatisticDto;
import ru.job4j.urlshortcut.model.Site;
import ru.job4j.urlshortcut.model.Statistic;

@Component
public class MapperDto {

    public SiteRegisterDto mapToRegisterDto(Site site, String login, String password) {
        SiteRegisterDto dto = new SiteRegisterDto();
        dto.setRegistration(site.isRegistration());
        dto.setLogin(login);
        dto.setPassword(password);
        return dto;
    }

    public SiteStatisticDto mapToStatisticDto(Statistic statistic) {
        SiteStatisticDto dto = new SiteStatisticDto();
        dto.setUrl(statistic.getUrl().getName());
        dto.setVisits(statistic.getVisits());
        return dto;
    }
}
