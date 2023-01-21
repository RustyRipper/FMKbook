package com.fmkbook.springbootbackend.models;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "REZERWACJA")
public class Rezerwacja {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "IDREZERWACJI", nullable = false)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UZYTKOWNIKIDUZYTKOWNIKA")
    private Uzytkownik uzytkownikiduzytkownika;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RABATKODRABATOWY")
    private Rabat rabatkodrabatowy;

    @Column(name = "CENAREZERWACJI")
    private Double cenarezerwacji;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "SEANSIDSEANSU", nullable = true)
    private Seans seansidseansu;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "SPOSOBPLATNOSCINAZWAMETODY", nullable = true)
    private Sposobplatnosci sposobplatnoscinazwametody;

    @OneToMany(mappedBy = "rezerwacjaidrezerwacji")
    private Set<Bilet> bilets = new LinkedHashSet<>();

    public Set<Bilet> getBilets() {
        return bilets;
    }

    public void setBilets(Set<Bilet> bilets) {
        this.bilets = bilets;
    }

    public Sposobplatnosci getSposobplatnoscinazwametody() {
        return sposobplatnoscinazwametody;
    }

    public void setSposobplatnoscinazwametody(Sposobplatnosci sposobplatnoscinazwametody) {
        this.sposobplatnoscinazwametody = sposobplatnoscinazwametody;
    }

    public Seans getSeansidseansu() {
        return seansidseansu;
    }

    public void setSeansidseansu(Seans seansidseansu) {
        this.seansidseansu = seansidseansu;
    }

    public Double getCenarezerwacji() {
        return cenarezerwacji;
    }

    public void setCenarezerwacji(Double cenarezerwacji) {
        this.cenarezerwacji = cenarezerwacji;
    }

    public Rabat getRabatkodrabatowy() {
        return rabatkodrabatowy;
    }

    public void setRabatkodrabatowy(Rabat rabatkodrabatowy) {
        this.rabatkodrabatowy = rabatkodrabatowy;
    }

    public Uzytkownik getUzytkownikiduzytkownika() {
        return uzytkownikiduzytkownika;
    }

    public void setUzytkownikiduzytkownika(Uzytkownik uzytkownikiduzytkownika) {
        this.uzytkownikiduzytkownika = uzytkownikiduzytkownika;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}