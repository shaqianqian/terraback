package com.terrastation.sha.VO;

import lombok.Data;

import java.util.List;

@Data
public class TerraiumsSensorGenereVO {

   private  List<TerrariumsGenereVO>Sensors;

   public List<TerrariumsGenereVO> getSensors() {
      return Sensors;
   }

   public void setSensors(List<TerrariumsGenereVO> sensors) {
      Sensors = sensors;
   }
}
