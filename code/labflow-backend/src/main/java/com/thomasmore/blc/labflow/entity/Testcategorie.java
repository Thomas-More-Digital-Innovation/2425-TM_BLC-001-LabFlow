package com.thomasmore.blc.labflow.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Testcategorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // voor auto-increment in SQLite
    private Long id;

    private String naam;

    private String kleur;

    // lege constructor
    public Testcategorie() {
    }

    // constructor met arguments
    public Testcategorie(String naam, String kleur) {
        this.naam = naam;
        this.kleur = kleur;
    }

    // getters en setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getKleur() {
        return kleur;
    }

    public void setKleur(String kleur) {
        this.kleur = kleur;
    }
}
