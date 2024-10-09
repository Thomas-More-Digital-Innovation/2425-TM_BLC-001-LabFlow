package com.thomasmore.blc.labflow.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    private String laborantNaam;

    private String laborantRnummer;

    // foreign key naar de usertabel
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany(mappedBy = "stalen")
    private List<Test> tests = new ArrayList<>();

    // lege constructor
    public Staal() {
    }

    // constructor met argumenten
    public Staal(int staalCode, String patientVoornaam, String patientAchternaam, Date patientGeboorteDatum, char patientGeslacht, String laborantNaam, String laborantRnummer, User user) {
        this.staalCode = staalCode;
        this.patientVoornaam = patientVoornaam;
        this.patientAchternaam = patientAchternaam;
        this.patientGeboorteDatum = patientGeboorteDatum;
        this.patientGeslacht = patientGeslacht;
        this.laborantNaam = laborantNaam;
        this.laborantRnummer = laborantRnummer;
        this.user = user;
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

    public String getLaborantNaam() {
        return laborantNaam;
    }

    public void setLaborantNaam(String laborantNaam) {
        this.laborantNaam = laborantNaam;
    }

    public String getLaborantRnummer() {
        return laborantRnummer;
    }

    public void setLaborantRnummer(String laborantRnummer) {
        this.laborantRnummer = laborantRnummer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Test> getTests() {
        return tests;
    }

    public void setTests(ArrayList<Test> tests) {
        this.tests = tests;
    }
}
