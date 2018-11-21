package com.terrastation.sha.Entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Lumiere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    private int moisDebut;

    private int moisFin;

    private int heureDebut ;

    private int heureFin ;

    private boolean etat ;

}