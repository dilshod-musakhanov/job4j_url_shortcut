package ru.job4j.urlshortcut.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "sites")
public class Site {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private boolean registration;

    @OneToOne
    @JoinColumn(name = "credential_id")
    private Credential credential;

    @OneToMany(mappedBy = "site")
    private List<Url> urls;

    public Site(String name, boolean registration, Credential credential) {
        this.name = name;
        this.registration = registration;
        this.credential = credential;
    }
}
