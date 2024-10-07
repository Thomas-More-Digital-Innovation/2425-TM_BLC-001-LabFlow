package com.thomasmore.blc.labflow.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Staal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // voor auto-increment in SQLite
    private Long id;

    private int staalCode;

    private String patientVoornaam;

    private String patientAchternaam;

    private Date patientGeboorteDatum;

    private char patientGeslacht;

    // foreign key naar de usertabel
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // lege constructor
    public Staal() {
    }

    // getters en setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getStaalCode() {
        return staalCode;
    }

    public void setStaalCode(int staalCode) {
        this.staalCode = staalCode;
    }

    public String getPatientVoornaam() {
        return patientVoornaam;
    }

    public void setPatientVoornaam(String patientVoornaam) {
        this.patientVoornaam = patientVoornaam;
    }

    public String getPatientAchternaam() {
        return patientAchternaam;
    }

    public void setPatientAchternaam(String patientAchternaam) {
        this.patientAchternaam = patientAchternaam;
    }

    public Date getPatientGeboorteDatum() {
        return patientGeboorteDatum;
    }

    public void setPatientGeboorteDatum(Date patientGeboorteDatum) {
        this.patientGeboorteDatum = patientGeboorteDatum;
    }

    public char getPatientGeslacht() {
        return patientGeslacht;
    }

    public void setPatientGeslacht(char patientGeslacht) {
        this.patientGeslacht = patientGeslacht;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
