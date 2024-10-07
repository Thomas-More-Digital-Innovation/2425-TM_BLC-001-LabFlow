package com.thomasmore.blc.labflow.entity;

import jakarta.persistence.*;
import org.springframework.lang.Nullable;


@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // voor auto-increment in SQLite
    private Long id;

    @Nullable
    private String wachtwoord;

    @Nullable
    private String email;

    private String voorNaam;

    private String achterNaam;

    // foreign key naar de rol tabel
    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;

    // lege constructor
    public User() {
    }

    // constructor voor popup "gegevens laborant"
    public User(String voorNaam, String achterNaam) {
        this.voorNaam = voorNaam;
        this.achterNaam = achterNaam;
    }

    // getters en setters
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
