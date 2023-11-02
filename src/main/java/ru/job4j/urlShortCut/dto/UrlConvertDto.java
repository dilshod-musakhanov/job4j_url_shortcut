package ru.job4j.urlshortcut.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class UrlConvertDto {

    private String code;
}
