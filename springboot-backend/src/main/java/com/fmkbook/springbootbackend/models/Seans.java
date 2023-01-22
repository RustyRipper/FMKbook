package com.fmkbook.springbootbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "SEANS")
@JsonIgnoreProperties(value = {"kinoidkina", "agent"})
public class Seans {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDSEANSU", nullable = false)
    private Integer id;

    //tylko dzien
    //@Column(name = "DATA", nullable = true)
    //private LocalDate data;
    //godzina
    @Column(name = "CZAS", nullable = false)
    private LocalTime czas;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "KINOIDKINA", nullable = true)
    private Kino kinoidkina;

//    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @ManyToOne(optional = true)
    @JoinColumn(name = "FILMIDFILMU", nullable = true)
    private Film filmidfilmu;

    @Column(name = "CENASEANSU", nullable = false)
    private Double cenaseansu;

    public Double getCenaseansu() {
        return cenaseansu;
    }

    public void setCenaseansu(Double cenaseansu) {
        this.cenaseansu = cenaseansu;
    }

    public Film getFilmidfilmu() {
        return filmidfilmu;
    }

    public void setFilmidfilmu(Film filmidfilmu) {
        this.filmidfilmu = filmidfilmu;
    }

    public Kino getKinoidkina() {
        return kinoidkina;
    }

    public void setKinoidkina(Kino kinoidkina) {
        this.kinoidkina = kinoidkina;
    }

    public LocalTime getCzas() {
        return czas;
    }

    public void setCzas(LocalTime czas) {
        this.czas = czas;
    }

    //public LocalDate getData() {
    //    return data;
    //}

    //public void setData(LocalDate data) {
    //    this.data = data;
    //}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}