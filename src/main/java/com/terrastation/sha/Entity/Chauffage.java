package com.terrastation.sha.Entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Chauffage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    private int dateDebut;

    private int dateFin;

    private int heureDebut;

    private int heureFin;

    private double min;

    private double max;



}