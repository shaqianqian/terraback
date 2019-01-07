package com.terrastation.sha.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Alarme {

    @Id
    private String type;

    private double min;
    private double max;
    private double variation;
    private double duree;
    private String message;



}