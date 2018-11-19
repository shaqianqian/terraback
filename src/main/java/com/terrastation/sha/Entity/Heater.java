package com.terrastation.sha.Entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Heater {
    @Id
    @GeneratedValue
    private int id;
    private String type;
    private boolean etat;


}
