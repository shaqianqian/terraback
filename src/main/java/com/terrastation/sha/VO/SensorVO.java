package com.terrastation.sha.VO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class SensorVO {

    int id;
    List<TerrariumGenereVO> values=new ArrayList<TerrariumGenereVO>();



}
