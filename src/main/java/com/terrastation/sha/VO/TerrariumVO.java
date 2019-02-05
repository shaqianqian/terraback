package com.terrastation.sha.VO;

import lombok.Data;

import java.util.Date;

@Data
public class TerrariumVO {


   private double value;


   private Date time;

   public double getValue() {
      return value;
   }

   public void setValue(double value) {
      this.value = value;
   }

   public Date getTime() {
      return time;
   }

   public void setTime(Date time) {
      this.time = time;
   }
}
