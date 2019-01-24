package com.terrastation.sha.VO;

import com.terrastation.sha.Entity.Pulverisation;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PulverisationVO {


   private String mode;
   private List<Pulverisation> pulverisationList;


}
