package com.terrastation.sha.VO;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class HumiditeVO {

   private int id;

   private double humidite;

   private Date createTime;

   private Date updateTime;


}
