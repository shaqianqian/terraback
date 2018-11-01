package com.terrastation.sha.VO;
import lombok.Data;
import java.util.List;
@Data
public class TemperaturesVO {

   private  List<TemperatureVO> TemperaturesVO;
   private  TemperatureVO max_temp;
   private  TemperatureVO  min_temp;


}
