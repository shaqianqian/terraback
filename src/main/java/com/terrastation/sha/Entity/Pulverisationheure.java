package com.terrastation.sha.Entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/*
 * La classe Puet une classe pour définir le
 * chauffage
 * */
@Data
@Entity
public class Pulverisationheure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    private int heure;
    private int duree;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHeure() {
        return heure;
    }

    public void setHeure(int heure) {
        this.heure = heure;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }
}