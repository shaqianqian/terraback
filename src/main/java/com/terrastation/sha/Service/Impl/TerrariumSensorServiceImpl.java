package com.terrastation.sha.Service.Impl;

import com.terrastation.sha.Enums.ResultEnum;
import com.terrastation.sha.Exception.IdNotExistException;
import com.terrastation.sha.Repositary.TerrariumRepositary;
import com.terrastation.sha.Service.TerrariumSensorService;
import com.terrastation.sha.VO.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j

public class TerrariumSensorServiceImpl implements TerrariumSensorService
{
    @Autowired
    private TerrariumRepositary terrariumRepositary;
    @Autowired
    private TerrariumServiceImpl terraiumServiceImpl;

    public SensorVO getCurrentHumiditeVO(int quantite) {


        TerrariumsVO humiditesVO= terraiumServiceImpl.GetCurrentHumiditesVO(quantite);
        List<TerrariumVO>  humidites=humiditesVO.getValues();
        List<TerrariumGenereVO> humiditeGeneres=new ArrayList<TerrariumGenereVO>();
        for(TerrariumVO t:humidites){

            TerrariumGenereVO humidteGenere=new TerrariumGenereVO();
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


        TerrariumsVO temperaturesVO= terraiumServiceImpl.GetCurrentTemperaturesVO(quantite);

        List<TerrariumVO>  temperatures=temperaturesVO.getValues();
        List<TerrariumGenereVO> temperatureGeneres=new ArrayList<TerrariumGenereVO>();
        for(TerrariumVO t:temperatures){

            TerrariumGenereVO temperatureGenere=new TerrariumGenereVO();
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
        TerrariumsVO terrariumsVO =new TerrariumsVO();
       if(id==2){
           terrariumsVO = terraiumServiceImpl.GetCurrentHumiditesVO(quantite);
       }else if(id==1){
           terrariumsVO = terraiumServiceImpl.GetCurrentTemperaturesVO(quantite);

       }
       else{ throw new IdNotExistException(ResultEnum.ID_NOT_EXIST);}
        List<TerrariumVO>  terraiumVOS= terrariumsVO.getValues();
        List<TerrariumGenereVO> terraiumGeneres=new ArrayList<TerrariumGenereVO>();
        for(TerrariumVO t:terraiumVOS){

            TerrariumGenereVO terrariumGenereVO =new TerrariumGenereVO();
            terrariumGenereVO.setX(t.getTime());
            terrariumGenereVO.setY(t.getValue());
            terraiumGeneres.add(terrariumGenereVO);
        }
        sensor.setId(id);
        sensor.setMeasurments(terraiumGeneres);

        return  sensor;

    }



}
