package com.mastertheboss.model;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mastertheboss.model.serializers.FilmSerializer;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "film")
@JsonSerialize(using = FilmSerializer.class)
public class Film implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "uri")
    private String uri;

    public Film() {
    }

    public Film(Film film) {
        this.title = film.getTitle();
        this.uri = film.getUri();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }


}
