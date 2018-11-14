package com.terrastation.sha.Service.Impl;

import com.terrastation.sha.Entity.Terraium;
import com.terrastation.sha.Enums.ResultEnum;
import com.terrastation.sha.Exception.TerraiumException;
import com.terrastation.sha.Repositary.TerraiumRepositary;
import com.terrastation.sha.Service.TerraiumService;
import com.terrastation.sha.Util.ResultUtil;
import com.terrastation.sha.VO.ResultVO;
import com.terrastation.sha.VO.TerraiumVO;
import com.terrastation.sha.VO.TerraiumsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
@Service
@Slf4j
public class TerraiumServiceImpl implements TerraiumService {
    @Autowired
    private TerraiumRepositary terraiumRepositary;

    public int getRowQuantity(){return terraiumRepositary.getRowQuantity();}

    public Optional<Terraium> findById(Integer Id) {
        return terraiumRepositary.findById(Id);
    }
    public void delete(Terraium t){

        terraiumRepositary.delete(t);
    }

    public List<Terraium> findAll() {
        return terraiumRepositary.findAll();
    }
    public Terraium save(Terraium t) {
        return terraiumRepositary.save(t);

    }
   public List<Terraium> getCurrentParametres(int quantity){
       if(quantity>terraiumRepositary.getRowQuantity()){
           throw new TerraiumException(ResultEnum.QUANTITE_ERROR);}

       return terraiumRepositary.findCurrentParametres(quantity);

   }
    //recuperer les humidites recentes
    public TerraiumsVO GetCurrentHumiditesVO(int quantite) {
        if(quantite>terraiumRepositary.getRowQuantity()){
            throw new TerraiumException(ResultEnum.QUANTITE_ERROR);}
        List<Terraium> terraiumList = terraiumRepositary.findCurrentParametres(quantite);
        Collections.reverse(terraiumList);
        List<TerraiumVO> humiditeVOList = new ArrayList<TerraiumVO>();
        for (Terraium t : terraiumList) {
            TerraiumVO humiditeVO = new TerraiumVO();
            humiditeVO.setValue(t.getHumidite());
            humiditeVO.setTime(t.getCreateTime());
            humiditeVOList.add(humiditeVO);


        }
        Terraium hum_max = terraiumRepositary.findMaxHumidites(quantite);
        TerraiumVO humiditeVO_max = new TerraiumVO();
        humiditeVO_max.setTime(hum_max.getCreateTime());
        humiditeVO_max.setValue(hum_max.getHumidite());


        Terraium hum__min = terraiumRepositary.findMinHumidites(quantite);
        TerraiumVO humiditeVO_min = new TerraiumVO();
        humiditeVO_min.setTime(hum__min.getCreateTime());
        humiditeVO_min.setValue(hum__min.getHumidite());


        TerraiumsVO humiditesVO = new TerraiumsVO();
        humiditesVO.setMax(humiditeVO_max);
        humiditesVO.setMin(humiditeVO_min);
        humiditesVO.setSymbol("%");
        humiditesVO.setType("humidite");
        humiditesVO.setValues(humiditeVOList);
        humiditesVO.setId(2);
        humiditesVO.setName("Humidite");


        return humiditesVO;

    }
//recuperer les temperatures recents
    public TerraiumsVO GetCurrentTemperaturesVO(int quantite) {
        if(quantite>terraiumRepositary.getRowQuantity()){
            throw new TerraiumException(ResultEnum.QUANTITE_ERROR);}
        List<Terraium> terraiumList = terraiumRepositary.findCurrentParametres(quantite);
        Collections.reverse(terraiumList);
        List<TerraiumVO> terraiumListVO = new ArrayList<TerraiumVO>();
        for (Terraium t : terraiumList) {
            TerraiumVO temperatureVO = new TerraiumVO();
            temperatureVO.setValue(t.getTemperature());
            temperatureVO.setTime(t.getCreateTime());
            terraiumListVO.add(temperatureVO);

        }
        Terraium temp_max = terraiumRepositary.findMaxTemperatures(quantite);
        TerraiumVO tempVO_max = new TerraiumVO();
        tempVO_max.setTime(temp_max.getCreateTime());
        tempVO_max.setValue(temp_max.getTemperature());


        Terraium temp_min = terraiumRepositary.findMinTemperatures(quantite);
        TerraiumVO tempVO_min = new TerraiumVO();
        tempVO_min.setTime(temp_min.getCreateTime());
        tempVO_min.setValue(temp_min.getTemperature());


        TerraiumsVO temperaturesVO = new TerraiumsVO();
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
