package com.terrastation.sha.VO;

import lombok.Data;

import java.util.List;

@Data
public class TerraiumsGenereVO {

   private String type;
   private String symbol;
   private  List<TerraiumGenereVO> Values;
   private String name;
   private int id;
   private  TerraiumGenereVO max;
   private  TerraiumGenereVO  min;





}
