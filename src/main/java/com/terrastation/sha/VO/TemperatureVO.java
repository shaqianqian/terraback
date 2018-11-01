package com.terrastation.sha.VO;

import lombok.Data;
import java.util.Date;

@Data
public class TemperatureVO {
    private int id;

    private double temperature;

    private Date createTime;

    private Date updateTime;




}
