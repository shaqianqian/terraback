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

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }
}
