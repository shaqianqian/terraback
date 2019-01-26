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



//   private TerrariumGenereVO max;
//   private TerrariumGenereVO min;


   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getSymbol() {
      return symbol;
   }

   public void setSymbol(String symbol) {
      this.symbol = symbol;
   }

   public String getIsProg() {
      return isProg;
   }

   public void setIsProg(String isProg) {
      this.isProg = isProg;
   }

   public String getIsOn() {
      return isOn;
   }

   public void setIsOn(String isOn) {
      this.isOn = isOn;
   }

   public List<TerrariumGenereVO> getValues() {
      return Values;
   }

   public void setValues(List<TerrariumGenereVO> values) {
      Values = values;
   }
}
