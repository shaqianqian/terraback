package com.terrastation.sha.Service;

import com.terrastation.sha.Enums.ResultEnum;
import com.terrastation.sha.Exception.IdNotExistException;
import com.terrastation.sha.Repositary.TerraiumRepositary;
import com.terrastation.sha.Util.ResultUtil;
import com.terrastation.sha.VO.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j

public class TerraiumServiceSensor {
    @Autowired
    private TerraiumRepositary terraiumRepositary;
    @Autowired
    private TerraiumService terraiumService;

    public SensorVO getCurrentHumiditeVO() {


        TerraiumsVO humiditesVO=terraiumService.GetCurrentHumiditesVO();
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
    public SensorVO getCurrentTemperaturesVO() {


        TerraiumsVO temperaturesVO=terraiumService.GetCurrentTemperaturesVO();

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

    public SensorVO getSensorByIdVO(int id) {
        SensorVO sensor=new SensorVO();
       if(id==2){
           TerraiumsVO humiditesVO=terraiumService.GetCurrentHumiditesVO();
           List<TerraiumVO>  humidites=humiditesVO.getValues();
           List<TerraiumGenereVO> humiditeGeneres=new ArrayList<TerraiumGenereVO>();
           for(TerraiumVO t:humidites){

               TerraiumGenereVO humidteGenere=new TerraiumGenereVO();
               humidteGenere.setX(t.getTime());
               humidteGenere.setY(t.getValue());
               humiditeGeneres.add(humidteGenere);
           }

           sensor.setId(2);
           sensor.setMeasurments(humiditeGeneres);

       }else if(id==1){


           TerraiumsVO humiditesVO=terraiumService.GetCurrentHumiditesVO();
           List<TerraiumVO>  humidites=humiditesVO.getValues();
           List<TerraiumGenereVO> humiditeGeneres=new ArrayList<TerraiumGenereVO>();
           for(TerraiumVO t:humidites){

               TerraiumGenereVO humidteGenere=new TerraiumGenereVO();
               humidteGenere.setX(t.getTime());
               humidteGenere.setY(t.getValue());
               humiditeGeneres.add(humidteGenere);
           }
           sensor.setId(2);
           sensor.setMeasurments(humiditeGeneres);

       }
       else{ throw new IdNotExistException(ResultEnum.ID_NOT_EXIST);}

        return  sensor;

    }



}
