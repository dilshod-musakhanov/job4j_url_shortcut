package ru.job4j.urlshortcut.dto;


import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class SiteRegisterDto {

    private boolean registration;
    private String login;
    private String password;

}
