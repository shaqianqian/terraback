package com.terrastation.sha.VO;

import lombok.Data;

import java.util.List;

@Data
public class TerrariumsGenereVO {

   private String type;
   private String symbol;
   private  List<TerrariumGenereVO> Values;
   private String name;
   private int id;
   private TerrariumGenereVO max;
   private TerrariumGenereVO min;





}
