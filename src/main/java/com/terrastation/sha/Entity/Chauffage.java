package com.terrastation.sha.Entity;

import com.terrastation.sha.Enums.ResultEnum;
import com.terrastation.sha.Exception.ParameterErrorException;
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

    private int moisDebut;

    private int moisFin;

    private int heureDebut;

    private int heureFin;

    private double min;

    private double max;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMoisDebut() {
        return moisDebut;
    }

    public void setMoisDebut(int moisDebut) {
        if (moisDebut > 12 || moisDebut < 1) {
            throw new ParameterErrorException(ResultEnum.Mois_range);
        } else {
            this.moisDebut = moisDebut;
        }
    }

    public int getMoisFin() {
        return moisFin;
    }

    public void setMoisFin(int moisFin) {
        if (moisFin > 12 || moisFin < 1) {
            throw new ParameterErrorException(ResultEnum.Mois_range);
        } else {
            this.moisFin = moisFin;
        }
    }

    public int getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(int heureDebut) {
        if (heureDebut > 24 || heureDebut < 0) {
            throw new ParameterErrorException(ResultEnum.Heure_range);
        } else {
            this.heureDebut = heureDebut;
        }
    }

    public int getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(int heureFin) {
        if (heureFin > 24 || heureFin < 0) {
            throw new ParameterErrorException(ResultEnum.Heure_range);
        }
        else{this.heureFin = heureFin;}
    }

    public double getMin() {

        return min;
    }

    public void setMin(double min) {
        if(min<0){ throw new ParameterErrorException(ResultEnum.Valeur_temperature);
        }
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        if(max<0){ throw new ParameterErrorException(ResultEnum.Valeur_temperature);
        }
        this.max = max;
    }
}