package com.terrastation.sha.VO;

import lombok.Data;

import java.util.List;

@Data
public class TerrariumsSensorVO {

   private  List<TerrariumsVO>Sensors;

   public List<TerrariumsVO> getSensors() {
      return Sensors;
   }

   public void setSensors(List<TerrariumsVO> sensors) {
      Sensors = sensors;
   }
}
