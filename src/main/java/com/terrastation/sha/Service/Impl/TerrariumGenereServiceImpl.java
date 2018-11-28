package com.terrastation.sha.Service.Impl;

import com.terrastation.sha.Entity.Terrarium;
import com.terrastation.sha.Enums.ResultEnum;
import com.terrastation.sha.Exception.TerraiumException;
import com.terrastation.sha.Repositary.TerrariumRepositary;
import com.terrastation.sha.Service.TerrariumGenereService;
import com.terrastation.sha.VO.TerrariumGenereVO;
import com.terrastation.sha.VO.TerrariumsGenereVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class TerrariumGenereServiceImpl implements TerrariumGenereService {
    @Autowired
    private TerrariumRepositary terrariumRepositary;
    public TerrariumsGenereVO GetCurrentHumiditesVO(int quantite) {
        if(quantite> terrariumRepositary.getRowQuantity()){
            throw new TerraiumException(ResultEnum.QUANTITE_ERROR);}
        List<Terrarium> terraiumList = terrariumRepositary.findCurrentParameters(quantite);
        Collections.reverse(terraiumList);
        List<TerrariumGenereVO> humiditeVOList = new ArrayList<TerrariumGenereVO>();
        for (Terrarium t : terraiumList) {
            TerrariumGenereVO humiditeVO = new TerrariumGenereVO();
            humiditeVO.setY(t.getHumidite());
            humiditeVO.setX(t.getCreateTime());
            humiditeVOList.add(humiditeVO);


        }
        Terrarium hum_max = terrariumRepositary.findMaxHumidites(quantite);
        TerrariumGenereVO humiditeVO_max = new TerrariumGenereVO();
        humiditeVO_max.setX(hum_max.getCreateTime());
        humiditeVO_max.setY(hum_max.getHumidite());


        Terrarium hum__min = terrariumRepositary.findMinHumidites(quantite);
        TerrariumGenereVO humiditeVO_min = new TerrariumGenereVO();
        humiditeVO_min.setX(hum__min.getCreateTime());
        humiditeVO_min.setY(hum__min.getHumidite());



        TerrariumsGenereVO humiditesVO=new TerrariumsGenereVO();
        humiditesVO.setMax(humiditeVO_max);
        humiditesVO.setMin(humiditeVO_min);
        humiditesVO.setSymbol("%");
        humiditesVO.setType("humidite");
        humiditesVO.setValues(humiditeVOList);
        humiditesVO.setId(2);
        humiditesVO.setName("Humidite");


        return humiditesVO;

    }

    public TerrariumsGenereVO GetCurrentTemperaturesVO(int quantite) {
        if(quantite> terrariumRepositary.getRowQuantity()){
            throw new TerraiumException(ResultEnum.QUANTITE_ERROR);}
        List<Terrarium> terraiumList = terrariumRepositary.findCurrentParameters(quantite);
        Collections.reverse(terraiumList);
        List<TerrariumGenereVO> terraiumListVO = new ArrayList<TerrariumGenereVO>();
        for (Terrarium t : terraiumList) {
            TerrariumGenereVO temperatureVO = new TerrariumGenereVO();
            temperatureVO.setY(t.getTemperature());
            temperatureVO.setX(t.getCreateTime());
            terraiumListVO.add(temperatureVO);

        }
        Terrarium temp_max = terrariumRepositary.findMaxTemperatures(quantite);
        TerrariumGenereVO tempVO_max = new TerrariumGenereVO();
        tempVO_max.setX(temp_max.getCreateTime());
        tempVO_max.setY(temp_max.getTemperature());



        Terrarium temp_min = terrariumRepositary.findMinTemperatures(quantite);
        TerrariumGenereVO tempVO_min = new TerrariumGenereVO();
        tempVO_min.setX(temp_min.getCreateTime());
        tempVO_min.setY(temp_min.getTemperature());



        TerrariumsGenereVO temperaturesVO=new TerrariumsGenereVO();
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
