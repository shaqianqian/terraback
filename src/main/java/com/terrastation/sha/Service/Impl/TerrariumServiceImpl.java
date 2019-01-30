package com.terrastation.sha.Service.Impl;
import com.terrastation.sha.Entity.Chauffage;
import com.terrastation.sha.Entity.Interrupteur;
import com.terrastation.sha.Entity.Terrarium;
import com.terrastation.sha.Enums.ResultEnum;
import com.terrastation.sha.Exception.TerraiumException;
import com.terrastation.sha.Repositary.ChauffageRepository;
import com.terrastation.sha.Repositary.InterrupteurRepository;
import com.terrastation.sha.Repositary.LumiereRepository;
import com.terrastation.sha.Repositary.TerrariumRepositary;
import com.terrastation.sha.Service.TerrariumService;
import com.terrastation.sha.VO.MaxminVO;
import com.terrastation.sha.VO.TerrariumVO;
import com.terrastation.sha.VO.TerrariumsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class TerrariumServiceImpl implements TerrariumService {
    @Autowired
    private TerrariumRepositary terrariumRepositary;


    public int getRowQuantity(){return terrariumRepositary.getRowQuantity();}
    public Terrarium getCurrentParameter(){
        return terrariumRepositary.getCurrentParameter().get(0);
    }
    public Optional<Terrarium> findById(Integer Id) {
        return terrariumRepositary.findById(Id);
    }
    public void delete(Terrarium t){

        terrariumRepositary.delete(t);
    }

    public List<Terrarium> findAll() {
        return terrariumRepositary.findAll();
    }
    public Terrarium save(Terrarium t) {
        return terrariumRepositary.save(t);

    }




   public List<Terrarium> getCurrentParameters(int quantity){
       if(quantity> terrariumRepositary.getRowQuantity()){
           throw new TerraiumException(ResultEnum.QUANTITE_ERROR);}

       return terrariumRepositary.findCurrentParameters(quantity);

   }
    //recuperer les humidites recentes
    public TerrariumsVO GetCurrentHumiditesVO(int quantite) {
        if(quantite> terrariumRepositary.getRowQuantity()){
            throw new TerraiumException(ResultEnum.QUANTITE_ERROR);}
        List<Terrarium> terraiumList = terrariumRepositary.findCurrentParameters(quantite);
        Collections.reverse(terraiumList);
        List<TerrariumVO> humiditeVOList = new ArrayList<TerrariumVO>();
        for (Terrarium t : terraiumList) {
            TerrariumVO humiditeVO = new TerrariumVO();
            humiditeVO.setValue(t.getHumidite());
            humiditeVO.setTime(t.getCreateTime());
            humiditeVOList.add(humiditeVO);


        }
        Terrarium hum_max = terrariumRepositary.findMaxHumidites(quantite);
        TerrariumVO humiditeVO_max = new TerrariumVO();
        humiditeVO_max.setTime(hum_max.getCreateTime());
        humiditeVO_max.setValue(hum_max.getHumidite());


        Terrarium hum__min = terrariumRepositary.findMinHumidites(quantite);
        TerrariumVO humiditeVO_min = new TerrariumVO();
        humiditeVO_min.setTime(hum__min.getCreateTime());
        humiditeVO_min.setValue(hum__min.getHumidite());


        TerrariumsVO humiditesVO = new TerrariumsVO();
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
    public TerrariumsVO GetCurrentTemperaturesVO(int quantite) {
        if(quantite> terrariumRepositary.getRowQuantity()){
            throw new TerraiumException(ResultEnum.QUANTITE_ERROR);}
        List<Terrarium> terraiumList = terrariumRepositary.findCurrentParameters(quantite);
        Collections.reverse(terraiumList);
        List<TerrariumVO> terraiumListVO = new ArrayList<TerrariumVO>();
        for (Terrarium t : terraiumList) {
            TerrariumVO temperatureVO = new TerrariumVO();
            temperatureVO.setValue(t.getTemperature());
            temperatureVO.setTime(t.getCreateTime());
            terraiumListVO.add(temperatureVO);

        }
        Terrarium temp_max = terrariumRepositary.findMaxTemperatures(quantite);
        TerrariumVO tempVO_max = new TerrariumVO();
        tempVO_max.setTime(temp_max.getCreateTime());
        tempVO_max.setValue(temp_max.getTemperature());


        Terrarium temp_min = terrariumRepositary.findMinTemperatures(quantite);
        TerrariumVO tempVO_min = new TerrariumVO();
        tempVO_min.setTime(temp_min.getCreateTime());
        tempVO_min.setValue(temp_min.getTemperature());


        TerrariumsVO temperaturesVO = new TerrariumsVO();
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
