package com.terrastation.sha.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class reptile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    
    private int age;

    private String name ;


}
