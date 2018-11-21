package com.terrastation.sha.Entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

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
    private int heureDebut ;
    private int heureFin;
    private int duree ;

}