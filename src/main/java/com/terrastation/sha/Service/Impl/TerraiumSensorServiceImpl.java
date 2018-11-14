package com.terrastation.sha.Service.Impl;

import com.terrastation.sha.Enums.ResultEnum;
import com.terrastation.sha.Exception.IdNotExistException;
import com.terrastation.sha.Repositary.TerraiumRepositary;
import com.terrastation.sha.Service.TerraiumSensorService;
import com.terrastation.sha.VO.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j

public class TerraiumSensorServiceImpl implements TerraiumSensorService
{
    @Autowired
    private TerraiumRepositary terraiumRepositary;
    @Autowired
    private TerraiumServiceImpl terraiumServiceImpl;

    public SensorVO getCurrentHumiditeVO(int quantite) {


        TerraiumsVO humiditesVO= terraiumServiceImpl.GetCurrentHumiditesVO(quantite);
        List<TerraiumVO>  humidites=humiditesVO.getValues();
        List<TerraiumGenereVO> humiditeGeneres=new ArrayList<TerraiumGenereVO>();
        for(TerraiumVO t:humidites){

            TerraiumGenereVO humidteGenere=new TerraiumGenereVO();
            humidteGenere.setX(t.getTime());
            humidteGenere.setY(t.getValue());
            humiditeGeneres.add(humidteGenere);
        }
        SensorVO humidite_sensor=new SensorVO();
        humidite_sensor.setId(2);
        humidite_sensor.setMeasurments(humiditeGeneres);

        return  humidite_sensor;

    }
    public SensorVO getCurrentTemperaturesVO(int quantite) {


        TerraiumsVO temperaturesVO= terraiumServiceImpl.GetCurrentTemperaturesVO(quantite);

        List<TerraiumVO>  temperatures=temperaturesVO.getValues();
        List<TerraiumGenereVO> temperatureGeneres=new ArrayList<TerraiumGenereVO>();
        for(TerraiumVO t:temperatures){

            TerraiumGenereVO temperatureGenere=new TerraiumGenereVO();
            temperatureGenere.setX(t.getTime());
            temperatureGenere.setY(t.getValue());
            temperatureGeneres.add(temperatureGenere);
        }
        SensorVO temperature_sensor=new SensorVO();
        temperature_sensor.setId(1);
        temperature_sensor.setMeasurments(temperatureGeneres);


        return temperature_sensor;

    }

    public SensorVO getSensorByIdVO(int id,int quantite) {
        SensorVO sensor=new SensorVO();
        TerraiumsVO terraiumsVO=new TerraiumsVO();
       if(id==2){
           terraiumsVO= terraiumServiceImpl.GetCurrentHumiditesVO(quantite);
       }else if(id==1){
           terraiumsVO= terraiumServiceImpl.GetCurrentTemperaturesVO(quantite);

       }
       else{ throw new IdNotExistException(ResultEnum.ID_NOT_EXIST);}
        List<TerraiumVO>  terraiumVOS=terraiumsVO.getValues();
        List<TerraiumGenereVO> terraiumGeneres=new ArrayList<TerraiumGenereVO>();
        for(TerraiumVO t:terraiumVOS){

            TerraiumGenereVO terraiumGenereVO=new TerraiumGenereVO();
            terraiumGenereVO.setX(t.getTime());
            terraiumGenereVO.setY(t.getValue());
            terraiumGeneres.add(terraiumGenereVO);
        }
        sensor.setId(id);
        sensor.setMeasurments(terraiumGeneres);

        return  sensor;

    }



}
