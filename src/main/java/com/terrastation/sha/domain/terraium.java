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

    public int getId() {
        return id;
    }

    public double getHumite() {
        return humite;
    }

    public double getTemperature() {
        return temperature;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setHumite(double humite) {
        this.humite = humite;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
