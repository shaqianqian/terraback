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
public class PulverisationInterrupteur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    private String mode = "";

    public PulverisationInterrupteur() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}