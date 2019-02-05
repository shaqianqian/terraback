package com.terrastation.sha.Entity;

import com.terrastation.sha.Enums.ResultEnum;
import com.terrastation.sha.Exception.ParameterErrorException;
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

    public Lumiere(int moisDebut, int moisFin, int heureDebut, int heureFin) {
        this.moisDebut = moisDebut;
        this.moisFin = moisFin;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
    }

    public Lumiere() {

    }

    public void setMoisDebut(int moisDebut) {
        if (moisDebut > 12 || moisDebut < 1) {
            throw new ParameterErrorException(ResultEnum.Mois_range);
        } else {
            this.moisDebut = moisDebut;
        }
    }
    public void setMoisFin(int moisFin) {
        if (moisFin > 12 || moisFin < 1) {
            throw new ParameterErrorException(ResultEnum.Mois_range);
        } else {
            this.moisFin = moisFin;
        }
    }
    public void setHeureDebut(int heureDebut) {
        if (heureDebut > 23 || heureDebut < 0) {
            throw new ParameterErrorException(ResultEnum.Heure_range);
        } else {
            this.heureDebut = heureDebut;
        }
    }
    public void setHeureFin(int heureFin) {
        if (heureFin > 23 || heureFin < 0) {
            throw new ParameterErrorException(ResultEnum.Heure_range);
        }
        else{this.heureFin = heureFin;}
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMoisDebut() {
        return moisDebut;
    }

    public int getMoisFin() {
        return moisFin;
    }

    public int getHeureDebut() {
        return heureDebut;
    }

    public int getHeureFin() {
        return heureFin;
    }
}