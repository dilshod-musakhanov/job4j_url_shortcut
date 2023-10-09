package ru.job4j.urlshortcut.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
public class UrlRegisteredDto {
    private boolean registration;
    @EqualsAndHashCode.Include
    private String login;
    @EqualsAndHashCode.Include
    private String password;

}
