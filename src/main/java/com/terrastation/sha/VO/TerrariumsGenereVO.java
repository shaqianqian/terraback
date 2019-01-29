package com.terrastation.sha.VO;

import lombok.Data;

import java.util.List;

@Data
public class TerrariumsGenereVO {

   private int id;
   private String name;
   private String symbol;
   private String isProg;
   private String isOn;
   private  List<TerrariumGenereVO> Values;
   private double min;
   private double max;
   private String progName;




//   private TerrariumGenereVO max;
//   private TerrariumGenereVO min;





}
