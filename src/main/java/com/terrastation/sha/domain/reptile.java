package com.terrastation.sha.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class reptile {
    @Id
    @GeneratedValue
    private int id;
    
    private int age;

    private String name ;


}
