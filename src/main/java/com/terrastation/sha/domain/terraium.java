package com.terrastation.sha.domain;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class terraium {
    @Id
    @GeneratedValue
    private int id;

    private double temperature;

    private double humite;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name= "createTime",updatable = false)
    @CreationTimestamp
    private Date createTime;



}
