package com.terrastation.sha.Service;

import com.terrastation.sha.VO.SensorVO;

public interface TerrariumSensorService {
    public SensorVO getCurrentHumiditeVO(int quantite);
    public SensorVO getCurrentTemperaturesVO(int quantite);
    public SensorVO getSensorByIdVO(int id,int quantite);


}
