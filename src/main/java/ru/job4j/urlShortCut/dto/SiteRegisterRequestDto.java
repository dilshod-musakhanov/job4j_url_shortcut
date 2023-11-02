package ru.job4j.urlshortcut.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Component
@Data
public class SiteRegisterRequestDto {

    @NotBlank(message = "Domain name is mandatory")
    @Pattern(regexp = "^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)+"
            + "([A-Za-z]|[A-Za-z][A-Za-z0-9\\-]*[A-Za-z0-9])$",
            message = "Domain name has to be in correct format")
    private String site;
}
