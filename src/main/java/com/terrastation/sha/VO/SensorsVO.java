package com.terrastation.sha.VO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SensorsVO {

    List<SensorAffichageVO> sensors=new ArrayList<SensorAffichageVO>();

    public List<SensorAffichageVO> getSensors() {
        return sensors;
    }

    public void setSensors(List<SensorAffichageVO> sensors) {
        this.sensors = sensors;
    }
}
