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


}