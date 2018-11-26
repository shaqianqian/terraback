package com.terrastation.sha.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Interrupteur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    private String type;
    private boolean etat;
    private boolean isProg;

}
