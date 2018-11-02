package com.terrastation.sha.VO;

import lombok.Data;

import java.util.List;

@Data
public class TerraiumsVO {

   private String type;
   private String symbol;
   private  List<TerraiumVO> Values;

   private  TerraiumVO max;
   private  TerraiumVO  min;





}
