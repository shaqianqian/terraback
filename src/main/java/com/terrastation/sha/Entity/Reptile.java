package com.terrastation.sha.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Reptile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    
    private int age;

    private String name ;


}
