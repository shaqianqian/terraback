package com.terrastation.sha.VO;

import lombok.Data;

import java.util.List;

@Data
public class TerrariumsVO {

   private String type;
   private String symbol;
   private  List<TerrariumVO> Values;
   private String name;
   private int id;
   private TerrariumVO max;
   private TerrariumVO min;


   public String getType() {
      return type;
   }

   public void setType(String type) {
      this.type = type;
   }

   public String getSymbol() {
      return symbol;
   }

   public void setSymbol(String symbol) {
      this.symbol = symbol;
   }

   public List<TerrariumVO> getValues() {
      return Values;
   }

   public void setValues(List<TerrariumVO> values) {
      Values = values;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public TerrariumVO getMax() {
      return max;
   }

   public void setMax(TerrariumVO max) {
      this.max = max;
   }

   public TerrariumVO getMin() {
      return min;
   }

   public void setMin(TerrariumVO min) {
      this.min = min;
   }

}
