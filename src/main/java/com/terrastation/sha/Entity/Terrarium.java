package com.terrastation.sha.Entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Terrarium {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name="temperature", columnDefinition="DOUBLE default 0",nullable=false)
    private double temperature=0;

    @Column(name="humidite", columnDefinition="DOUBLE default 0",nullable=false)
    private double humidite=0;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name= "createTime",updatable = false)
    private Date createTime;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updateTime")
    private Date updateTime;



}
