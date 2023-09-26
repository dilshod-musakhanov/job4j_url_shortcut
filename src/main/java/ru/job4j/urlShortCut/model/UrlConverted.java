package ru.job4j.urlshortcut.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "url_converted")
public class UrlConverted {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "url_shortcut")
    private String urlShortcut;
    private String url;

    @Column(name = "site_id")
    private int siteId;
    private int visits;
}
