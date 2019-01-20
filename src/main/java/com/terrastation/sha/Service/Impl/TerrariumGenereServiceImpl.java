package com.terrastation.sha.Service.Impl;

import com.terrastation.sha.Entity.Interrupteur;
import com.terrastation.sha.Entity.Terrarium;
import com.terrastation.sha.Enums.ResultEnum;
import com.terrastation.sha.Exception.TerraiumException;
import com.terrastation.sha.Repositary.InterrupteurRepository;
import com.terrastation.sha.Repositary.TerrariumRepositary;
import com.terrastation.sha.Service.InterrupteurService;
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
    @Autowired
    private InterrupteurRepository interrupteurRepository;
    @Autowired
    private InterrupteurService interrupteurService;

    public TerrariumsGenereVO GetCurrentHumiditesVO(int quantite) {
        if(quantite> terrariumRepositary.getRowQuantity()){
            throw new TerraiumException(ResultEnum.QUANTITE_ERROR);}
        List<Terrarium> terraiumList = terrariumRepositary.findCurrentParameters(quantite);
        Collections.reverse(terraiumList);
        List<TerrariumGenereVO> humiditeVOList = new ArrayList<TerrariumGenereVO>();
        for (Terrarium t : terraiumList) {
            TerrariumGenereVO humiditeVO = new TerrariumGenereVO();
            humiditeVO.setY(t.getHumidite());
            humiditeVO.setT(t.getCreateTime());
            humiditeVOList.add(humiditeVO);


        }
         Terrarium hum_max = terrariumRepositary.findMaxHumidites(quantite);
//        TerrariumGenereVO humiditeVO_max = new TerrariumGenereVO();
//        humiditeVO_max.setT(hum_max.getCreateTime());
//        humiditeVO_max.setY(hum_max.getHumidite());
//
//
        Terrarium hum_min = terrariumRepositary.findMinHumidites(quantite);
//        TerrariumGenereVO humiditeVO_min = new TerrariumGenereVO();
//        humiditeVO_min.setT(hum__min.getCreateTime());
//        humiditeVO_min.setY(hum__min.getHumidite());



        TerrariumsGenereVO humiditesVO=new TerrariumsGenereVO();
        Interrupteur chauffage=interrupteurService.getControleInterrupteur("chauffage");

        if(chauffage.isEtat()){
            humiditesVO.setIsOn("true");
        }
        else{ humiditesVO.setIsOn("false");}
        if(chauffage.isProg()){
            humiditesVO.setIsProg("true");
        }
        else{ humiditesVO.setIsProg("false");}


//        humiditesVO.setMax(humiditeVO_max);
//        humiditesVO.setMin(humiditeVO_min);
        humiditesVO.setSymbol("%");
        humiditesVO.setValues(humiditeVOList);
        humiditesVO.setId(2);
        humiditesVO.setName("Humidite");
        humiditesVO.setMax(hum_max.getHumidite());
        humiditesVO.setMin(hum_min.getHumidite());


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
            temperatureVO.setT(t.getCreateTime());
            terraiumListVO.add(temperatureVO);

        }
        Terrarium temp_max = terrariumRepositary.findMaxTemperatures(quantite);
//        TerrariumGenereVO tempVO_max = new TerrariumGenereVO();
//        tempVO_max.setT(temp_max.getCreateTime());
//        tempVO_max.setY(temp_max.getTemperature());
//
//
//
        Terrarium temp_min = terrariumRepositary.findMinTemperatures(quantite);
//        TerrariumGenereVO tempVO_min = new TerrariumGenereVO();
//        tempVO_min.setT(temp_min.getCreateTime());
//        tempVO_min.setY(temp_min.getTemperature());



        TerrariumsGenereVO temperaturesVO=new TerrariumsGenereVO();

        temperaturesVO.setSymbol("°C");
        Interrupteur chauffage=interrupteurService.getControleInterrupteur("chauffage");
        temperaturesVO.setMax(temp_max.getTemperature());
        temperaturesVO.setMin(temp_min.getTemperature());
        if(chauffage.isEtat()){
            temperaturesVO.setIsOn("true");
        }
        else{ temperaturesVO.setIsOn("false");}
        if(chauffage.isProg()){
            temperaturesVO.setIsProg("true");
        }
        else{ temperaturesVO.setIsProg("false");}
//        temperaturesVO.setMax(tempVO_max);
//        temperaturesVO.setMin(tempVO_min);
        temperaturesVO.setValues(terraiumListVO);
        temperaturesVO.setId(1);
        temperaturesVO.setName("Temperature");

        return temperaturesVO;

    }



    public List<Terrarium> getDonneeUneSemaine() {
        List<Terrarium> terrariums=terrariumRepositary.donneUneSemaine();
        List<Terrarium> newTerrariums=new ArrayList<Terrarium>();
        int quantite=10;
        int distance=terrariums.size()/quantite;
        for(int i=0;i<terrariums.size();i=i+distance){
            Terrarium t=terrariums.get(i);
            newTerrariums.add(t);

        }
        return newTerrariums;}

    public TerrariumsGenereVO GetUneSemaineTemperaturesVO() {
        List<Terrarium> terraiumList = this.getDonneeUneSemaine();
        List<TerrariumGenereVO> terraiumListVO = new ArrayList<TerrariumGenereVO>();
        for (Terrarium t : terraiumList) {
            TerrariumGenereVO temperatureVO = new TerrariumGenereVO();
            temperatureVO.setY(t.getTemperature());
            temperatureVO.setT(t.getCreateTime());
            terraiumListVO.add(temperatureVO);

        }

        TerrariumsGenereVO temperaturesVO=new TerrariumsGenereVO();

        temperaturesVO.setSymbol("°C");
        Interrupteur chauffage=interrupteurService.getControleInterrupteur("chauffage");

        if(chauffage.isEtat()){
            temperaturesVO.setIsOn("true");
        }
        else{ temperaturesVO.setIsOn("false");}
        if(chauffage.isProg()){
            temperaturesVO.setIsProg("true");
        }
        else{ temperaturesVO.setIsProg("false");}
        temperaturesVO.setValues(terraiumListVO);
        temperaturesVO.setId(1);
        temperaturesVO.setName("Temperature");

        return temperaturesVO;

    }

    public TerrariumsGenereVO GetUneSemaineHumiditesVO() {

        List<Terrarium> terraiumList = this.getDonneeUneSemaine();
        List<TerrariumGenereVO> humiditeVOList = new ArrayList<TerrariumGenereVO>();
        for (Terrarium t : terraiumList) {
            TerrariumGenereVO humiditeVO = new TerrariumGenereVO();
            humiditeVO.setY(t.getHumidite());
            humiditeVO.setT(t.getCreateTime());
            humiditeVOList.add(humiditeVO);


        }
        TerrariumsGenereVO humiditesVO=new TerrariumsGenereVO();
        Interrupteur chauffage=interrupteurService.getControleInterrupteur("chauffage");

        if(chauffage.isEtat()){
            humiditesVO.setIsOn("true");
        }
        else{ humiditesVO.setIsOn("false");}
        if(chauffage.isProg()){
            humiditesVO.setIsProg("true");
        }
        else{ humiditesVO.setIsProg("false");}
        humiditesVO.setSymbol("%");
        humiditesVO.setValues(humiditeVOList);
        humiditesVO.setId(2);
        humiditesVO.setName("Humidite");


        return humiditesVO;

    }

    public List<Terrarium> getDonneeUnMois() {
        List<Terrarium> terrariums=terrariumRepositary.donneUnMois();
        List<Terrarium> newTerrariums=new ArrayList<Terrarium>();
        int quantite=10;
        int distance=terrariums.size()/quantite;
        for(int i=0;i<terrariums.size();i=i+distance){
            Terrarium t=terrariums.get(i);
            newTerrariums.add(t);

        }
        return newTerrariums;}

    public TerrariumsGenereVO GetUnMoisTemperaturesVO() {
        List<Terrarium> terraiumList = this.getDonneeUnMois();
        List<TerrariumGenereVO> terraiumListVO = new ArrayList<TerrariumGenereVO>();
        for (Terrarium t : terraiumList) {
            TerrariumGenereVO temperatureVO = new TerrariumGenereVO();
            temperatureVO.setY(t.getTemperature());
            temperatureVO.setT(t.getCreateTime());
            terraiumListVO.add(temperatureVO);

        }

        TerrariumsGenereVO temperaturesVO=new TerrariumsGenereVO();

        temperaturesVO.setSymbol("°C");
        Interrupteur chauffage=interrupteurService.getControleInterrupteur("chauffage");

        if(chauffage.isEtat()){
            temperaturesVO.setIsOn("true");
        }
        else{ temperaturesVO.setIsOn("false");}
        if(chauffage.isProg()){
            temperaturesVO.setIsProg("true");
        }
        else{ temperaturesVO.setIsProg("false");}
        temperaturesVO.setValues(terraiumListVO);
        temperaturesVO.setId(1);
        temperaturesVO.setName("Temperature");

        return temperaturesVO;

    }

    public TerrariumsGenereVO GetUnMoisHumiditesVO() {

        List<Terrarium> terraiumList = this.getDonneeUnMois();
        List<TerrariumGenereVO> humiditeVOList = new ArrayList<TerrariumGenereVO>();
        for (Terrarium t : terraiumList) {
            TerrariumGenereVO humiditeVO = new TerrariumGenereVO();
            humiditeVO.setY(t.getHumidite());
            humiditeVO.setT(t.getCreateTime());
            humiditeVOList.add(humiditeVO);


        }
        TerrariumsGenereVO humiditesVO=new TerrariumsGenereVO();
        Interrupteur chauffage=interrupteurService.getControleInterrupteur("chauffage");

        if(chauffage.isEtat()){
            humiditesVO.setIsOn("true");
        }
        else{ humiditesVO.setIsOn("false");}
        if(chauffage.isProg()){
            humiditesVO.setIsProg("true");
        }
        else{ humiditesVO.setIsProg("false");}
        humiditesVO.setSymbol("%");
        humiditesVO.setValues(humiditeVOList);
        humiditesVO.setId(2);
        humiditesVO.setName("Humidite");


        return humiditesVO;

    }





}
