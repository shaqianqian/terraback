package com.terrastation.sha.Entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/*
 * La classe Puet une classe pour définir le
 * chauffage
 * */
@Data
@Entity
public class Pulverisation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    private int moisDebut;
    private int moisFin;
    private int heureDebut;
    private int heureFin;
    private String mode = "";
    private int duree_hygrometrie;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "pulverisationId")
    private List<Pulverisationheure> pulverisationheure;

    private double taux_hygrometrie_min;
    private double taux_hygrometrie_max;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMoisDebut() {
        return moisDebut;
    }

    public void setMoisDebut(int moisDebut) {
        this.moisDebut = moisDebut;
    }

    public int getMoisFin() {
        return moisFin;
    }

    public void setMoisFin(int moisFin) {
        this.moisFin = moisFin;
    }

    public int getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(int heureDebut) {
        this.heureDebut = heureDebut;
    }

    public int getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(int heureFin) {
        this.heureFin = heureFin;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public int getDuree_hygrometrie() {
        return duree_hygrometrie;
    }

    public void setDuree_hygrometrie(int duree_hygrometrie) {
        this.duree_hygrometrie = duree_hygrometrie;
    }

    public List<Pulverisationheure> getPulverisationheure() {
        return pulverisationheure;
    }

    public void setPulverisationheure(List<Pulverisationheure> pulverisationheure) {
        this.pulverisationheure = pulverisationheure;
    }

    public double getTaux_hygrometrie_min() {
        return taux_hygrometrie_min;
    }

    public void setTaux_hygrometrie_min(double taux_hygrometrie_min) {
        this.taux_hygrometrie_min = taux_hygrometrie_min;
    }

    public double getTaux_hygrometrie_max() {
        return taux_hygrometrie_max;
    }

    public void setTaux_hygrometrie_max(double taux_hygrometrie_max) {
        this.taux_hygrometrie_max = taux_hygrometrie_max;
    }
}