package com.thomasmore.blc.labflow.entity;

import jakarta.persistence.*;

@Entity
public class Referentiewaarde {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // voor auto-increment in SQLite
    private Long id;

    private int referentieWaardeMin;

    private int referentieWaardeMax;

    // foreign key naar de test tabel
    @ManyToOne
    @JoinColumn(name = "test_id", nullable = false)
    private Test test;

    // Lege constructor
    public Referentiewaarde() {
    }

    // constructor met argumenten
    public Referentiewaarde(int referentieWaardeMin, int referentieWaardeMax, Test test) {
        this.referentieWaardeMin = referentieWaardeMin;
        this.referentieWaardeMax = referentieWaardeMax;
        this.test = test;
    }

    // getters & setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getReferentieWaardeMin() {
        return referentieWaardeMin;
    }

    public void setReferentieWaardeMin(int referentieWaardeMin) {
        this.referentieWaardeMin = referentieWaardeMin;
    }

    public int getReferentieWaardeMax() {
        return referentieWaardeMax;
    }

    public void setReferentieWaardeMax(int referentieWaardeMax) {
        this.referentieWaardeMax = referentieWaardeMax;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }
}
