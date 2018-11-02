package com.terrastation.sha.VO;

import lombok.Data;

import java.util.Date;

@Data
public class TerraiumVO {

   private int id;

   private String type;
   private double valeur;

   private String symbol;

   private Date createTime;


   private Date updateTime;


}
