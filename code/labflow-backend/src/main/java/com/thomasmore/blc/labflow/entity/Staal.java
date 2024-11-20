package com.thomasmore.blc.labflow.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.*;

@Entity
public class Staal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // voor auto-increment in SQLite
    private Long id;

    @Column(unique = true)
    private Long staalCode;

    private String patientVoornaam;

    private String patientAchternaam;

    private LocalDate patientGeboorteDatum;

    private char patientGeslacht;

    private String laborantNaam;

    private String laborantRnummer;

    private Date aanmaakDatum;

    // foreign key naar de usertabel
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "staal", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @JsonManagedReference
    private List<StaalTest> registeredTests = new ArrayList<>();

    // lege constructor
    public Staal() {
        this.aanmaakDatum = new Date();
    }

    // constructor voor het registreren van een staal zonder tests
    public Staal(Long staalCode, String patientVoornaam, String patientAchternaam, LocalDate patientGeboorteDatum,
                 char patientGeslacht, String laborantNaam, String laborantRnummer, User user) {
        this.staalCode = staalCode;
        this.patientVoornaam = patientVoornaam;
        this.patientAchternaam = patientAchternaam;
        this.patientGeboorteDatum = patientGeboorteDatum;
        this.patientGeslacht = patientGeslacht;
        this.laborantNaam = laborantNaam;
        this.laborantRnummer = laborantRnummer;
        this.user = user;
        this.aanmaakDatum = new Date();
    }

    // constructor voor het registreren van een staal met tests
    public Staal(Long staalCode, String patientVoornaam, String patientAchternaam, LocalDate patientGeboorteDatum,
                 char patientGeslacht, String laborantNaam, String laborantRnummer, User user,
                 List<StaalTest> registeredTests) {
        this.staalCode = staalCode;
        this.patientVoornaam = patientVoornaam;
        this.patientAchternaam = patientAchternaam;
        this.patientGeboorteDatum = patientGeboorteDatum;
        this.patientGeslacht = patientGeslacht;
        this.laborantNaam = laborantNaam;
        this.laborantRnummer = laborantRnummer;
        this.user = user;
        this.setRegisteredTests(registeredTests); // Use setter to ensure proper association
        this.aanmaakDatum = new Date();
    }

    // getters en setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStaalCode() {
        return staalCode;
    }

    public void setStaalCode(Long staalCode) {
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

    public LocalDate getPatientGeboorteDatum() {
        return patientGeboorteDatum;
    }

    public void setPatientGeboorteDatum(LocalDate patientGeboorteDatum) {
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

    public Date getAanmaakDatum() {
        return aanmaakDatum;
    }

    public void setAanmaakDatum(Date aanmaakDatum) {
        this.aanmaakDatum = aanmaakDatum;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<StaalTest> getRegisteredTests() {
        return registeredTests;
    }

    public void setRegisteredTests(List<StaalTest> newTests) {
        // Verwijderen bestaande tests
        registeredTests.removeIf(existingTest -> !newTests.contains(existingTest));

        // Nieuwe tests toevoegen
        for (StaalTest newTest : newTests) {
            if (!registeredTests.contains(newTest)) {
                registeredTests.add(newTest);
                newTest.setStaal(this);
            }
        }
    }

    // methodes voor het toevoegen en verwijderen van tests gekoppeld aan één staal
    public void addRegisteredTest(StaalTest test) {
        registeredTests.add(test);
        test.setStaal(this);
    }

    public void removeRegisteredTest(StaalTest test) {
        registeredTests.remove(test);
        test.setStaal(null);
    }
}
