package com.terrastation.sha.domain;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/*
 * La classe Lumiere et une classe pour définir le
 * lumière
 * */
@Data
@Entity
public class Lumiere {
    @Id
    @GeneratedValue
    private int id;

    private Date dateDebut;

    private Date dateFin;

    private Date heureOn ;

    private Date heureOff ;

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

    public Date getHeureOn() {
        return heureOn;
    }

    public Date getHeureOff() {
        return heureOff;
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

    public void setHeureOn(Date heureOn) {
        this.heureOn = heureOn;
    }

    public void setHeureOff(Date heureOff) {
        this.heureOff = heureOff;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }
}

