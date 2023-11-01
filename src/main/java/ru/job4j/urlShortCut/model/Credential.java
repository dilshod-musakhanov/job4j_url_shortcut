package ru.job4j.urlshortcut.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@Entity
@Table(name = "credentials")
public class Credential {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "login must be non null!")
    private String login;

    @NotBlank(message = "password must be non null!")
    private String password;

    public Credential(String login, String password) {
        this.login = login;
        this.password = password;
    }

}
