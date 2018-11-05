package com.terrastation.sha.domain;


import lombok.Data;

import javax.persistence.*;
/*
* La classe Prereglages et une classe pour définir les préréglages du
* réptile
* */
@Data
@Entity
public class Prereglages {
    @Id
    @GeneratedValue
    private int id;

    private String typeHabitat ;

    private String animal;

    private double temperature ;

    private double humidite ;

    private double pulverisation ;

    public String getTypeHabitat() {
        return typeHabitat;
    }

    public String getAnimal() {
        return animal;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getHumidite() {
        return humidite;
    }

    public double getPulverisation() {
        return pulverisation;
    }


    public void setTypeHabitat(String typeHabitat) {
        this.typeHabitat = typeHabitat;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setHumidite(double humidite) {
        this.humidite = humidite;
    }

    public void setPulverisation(double pulverisation) {
        this.pulverisation = pulverisation;
    }
}
