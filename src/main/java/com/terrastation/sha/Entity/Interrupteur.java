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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    public boolean isProg() {
        return isProg;
    }

    public void setProg(boolean prog) {
        isProg = prog;
    }
}
