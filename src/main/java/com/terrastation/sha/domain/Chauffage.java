package com.terrastation.sha.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/*
 * La classe Prereglages et une classe pour définir le
 * chauffage
 * */
@Data
@Entity
public class Chauffage {
    @Id
    @GeneratedValue
    private int id;

    private Date dateDebut;

    private Date dateFin;

    private double min ;

    private double max ;

    private boolean etat ;

    public int getId() {
        return id;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }
}
