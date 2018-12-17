package com.terrastation.sha.Entity;

import com.terrastation.sha.Enums.ResultEnum;
import com.terrastation.sha.Exception.ParameterErrorException;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Profil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    private String email;



}