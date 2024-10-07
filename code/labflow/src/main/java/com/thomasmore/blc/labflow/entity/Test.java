package com.thomasmore.blc.labflow.entity;

import jakarta.persistence.*;

@Entity
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // voor auto-increment in SQLite
    private Long id;

    private int testCode;

    private String naam;

    private int normaleWaardeMin;

    private int normaleWaardeMax;

    // foreign key naar de usertabel
    @ManyToOne
    @JoinColumn(name = "staal_id", nullable = true)
    private Staal staal;

    // foreign key naar de eenheidtabel
    @ManyToOne
    @JoinColumn(name = "eenheid_id", nullable = false)
    private Eenheid eenheid;

    // foreign key naar de eenheidtabel
    @ManyToOne
    @JoinColumn(name = "testcategorie_id", nullable = false)
    private Testcategorie testcategorie;

    // lege constructor
    public Test() {
    }

    // getters en setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTestCode() {
        return testCode;
    }

    public void setTestCode(int testCode) {
        this.testCode = testCode;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public int getNormaleWaardeMin() {
        return normaleWaardeMin;
    }

    public void setNormaleWaardeMin(int normaleWaardeMin) {
        this.normaleWaardeMin = normaleWaardeMin;
    }

    public int getNormaleWaardeMax() {
        return normaleWaardeMax;
    }

    public void setNormaleWaardeMax(int normaleWaardeMax) {
        this.normaleWaardeMax = normaleWaardeMax;
    }

    public Staal getStaal() {
        return staal;
    }

    public void setStaal(Staal staal) {
        this.staal = staal;
    }
}
