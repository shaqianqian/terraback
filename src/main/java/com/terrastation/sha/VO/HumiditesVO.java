package com.terrastation.sha.VO;

import lombok.Data;

import java.util.List;

@Data
public class HumiditesVO {

   private  List<HumiditeVO> humiditesVO;
   private  HumiditeVO max_hum;
   private   HumiditeVO min_hum;


}
