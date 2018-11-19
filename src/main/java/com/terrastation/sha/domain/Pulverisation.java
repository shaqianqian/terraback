package com.terrastation.sha.domain;

package com.terrastation.sha.domain;

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
    @GeneratedValue
    private int id;
    private Date date;
    private Date heure ;
    private int durée ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getHeure() {
        return heure;
    }

    public void setHeure(Date heure) {
        this.heure = heure;
    }

    public int getDurée() {
        return durée;
    }

    public void setDurée(int durée) {
        this.durée = durée;
    }
}
