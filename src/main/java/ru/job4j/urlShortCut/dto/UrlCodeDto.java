package ru.job4j.urlshortcut.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;

@Component
@Data
public class UrlCodeDto {

    @NotBlank
    private String code;
}
