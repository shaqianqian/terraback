package com.terrastation.sha.VO;

import lombok.Data;

import java.util.Date;

@Data
public class TerrariumGenereVO {


   private double y;
   private Date t;

   public double getY() {
      return y;
   }

   public void setY(double y) {
      this.y = y;
   }

   public Date getT() {
      return t;
   }

   public void setT(Date t) {
      this.t = t;
   }
}
