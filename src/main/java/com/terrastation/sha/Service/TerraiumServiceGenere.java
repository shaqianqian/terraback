package com.terrastation.sha.Service;

import com.terrastation.sha.Entity.Terraium;
import com.terrastation.sha.Repositary.TerraiumRepositary;
import com.terrastation.sha.VO.TerraiumGenereVO;
import com.terrastation.sha.VO.TerraiumVO;
import com.terrastation.sha.VO.TerraiumsGenereVO;
import com.terrastation.sha.VO.TerraiumsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class TerraiumServiceGenere {
    @Autowired
    private TerraiumRepositary terraiumRepositary;
    public TerraiumsGenereVO  GetCurrentHumiditesVO() {
        List<Terraium> terraiumList = terraiumRepositary.findCurrentTemperatures(6);
        Collections.reverse(terraiumList);
        List<TerraiumGenereVO> humiditeVOList = new ArrayList<TerraiumGenereVO>();
        for (Terraium t : terraiumList) {
            TerraiumGenereVO humiditeVO = new TerraiumGenereVO();
            humiditeVO.setY(t.getHumidite());
            humiditeVO.setX(t.getCreateTime());
            humiditeVOList.add(humiditeVO);


        }
        Terraium hum_max = terraiumRepositary.findMaxHumidites(6);
        TerraiumGenereVO humiditeVO_max = new TerraiumGenereVO();
        humiditeVO_max.setX(hum_max.getCreateTime());
        humiditeVO_max.setY(hum_max.getHumidite());


        Terraium hum__min = terraiumRepositary.findMinHumidites(6);
        TerraiumGenereVO humiditeVO_min = new TerraiumGenereVO();
        humiditeVO_min.setX(hum__min.getCreateTime());
        humiditeVO_min.setY(hum__min.getHumidite());



        TerraiumsGenereVO humiditesVO=new  TerraiumsGenereVO();
        humiditesVO.setMax(humiditeVO_max);
        humiditesVO.setMin(humiditeVO_min);
        humiditesVO.setSymbol("%");
        humiditesVO.setType("humidite");
        humiditesVO.setValues(humiditeVOList);
        humiditesVO.setId(2);
        humiditesVO.setName("Humidite");


        return humiditesVO;

    }

    public TerraiumsGenereVO GetCurrentTemperaturesVO() {
        List<Terraium> terraiumList = terraiumRepositary.findCurrentTemperatures(6);
        Collections.reverse(terraiumList);
        List<TerraiumGenereVO> terraiumListVO = new ArrayList<TerraiumGenereVO>();
        for (Terraium t : terraiumList) {
            TerraiumGenereVO temperatureVO = new TerraiumGenereVO();
            temperatureVO.setY(t.getTemperature());
            temperatureVO.setX(t.getCreateTime());
            terraiumListVO.add(temperatureVO);

        }
        Terraium temp_max = terraiumRepositary.findMaxTemperatures(6);
        TerraiumGenereVO tempVO_max = new TerraiumGenereVO();
        tempVO_max.setX(temp_max.getCreateTime());
        tempVO_max.setY(temp_max.getTemperature());



        Terraium temp_min = terraiumRepositary.findMinTemperatures(6);
        TerraiumGenereVO tempVO_min = new TerraiumGenereVO();
        tempVO_min.setX(temp_min.getCreateTime());
        tempVO_min.setY(temp_min.getTemperature());



        TerraiumsGenereVO temperaturesVO=new TerraiumsGenereVO();
        temperaturesVO.setSymbol("°C");
        temperaturesVO.setType("temperature");
        temperaturesVO.setMax(tempVO_max);
        temperaturesVO.setMin(tempVO_min);
        temperaturesVO.setValues(terraiumListVO);
        temperaturesVO.setId(1);
        temperaturesVO.setName("Temperature");

        return temperaturesVO;

    }





}
