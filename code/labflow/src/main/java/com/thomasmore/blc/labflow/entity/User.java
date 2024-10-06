package com.thomasmore.blc.labflow.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.lang.Nullable;


@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // For auto-increment in SQLite
    private Long id;

    @Nullable
    private String wachtwoord;

    @Nullable
    private String email;

    private String voorNaam;

    private String achterNaam;


    public User() {
    }

    // constructor voor popup "gegevens laborant"
    public User(Long id, String voorNaam, String achterNaam) {
        this.voorNaam = voorNaam;
        this.achterNaam = achterNaam;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWachtwoord() {
        return wachtwoord;
    }

    public void setWachtwoord(String wachtwoord) {
        this.wachtwoord = wachtwoord;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVoorNaam() {
        return voorNaam;
    }

    public void setVoorNaam(String voorNaam) {
        this.voorNaam = voorNaam;
    }

    public String getAchterNaam() {
        return achterNaam;
    }

    public void setAchterNaam(String achterNaam) {
        this.achterNaam = achterNaam;
    }

    public String getFullName() {
        return voorNaam + " " + achterNaam;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + getFullName() + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
