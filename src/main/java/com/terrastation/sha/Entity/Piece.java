package com.terrastation.sha.Entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Piece {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String type;
    private boolean etat;


}
