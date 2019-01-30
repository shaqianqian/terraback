package com.terrastation.sha.Service.Impl;

import com.terrastation.sha.Entity.*;
import com.terrastation.sha.Enums.ResultEnum;
import com.terrastation.sha.Exception.TerraiumException;
import com.terrastation.sha.Repositary.*;
import com.terrastation.sha.Service.InterrupteurService;
import com.terrastation.sha.Service.TerrariumGenereService;
import com.terrastation.sha.Service.TerrariumService;
import com.terrastation.sha.VO.MaxminVO;
import com.terrastation.sha.VO.TerrariumGenereVO;
import com.terrastation.sha.VO.TerrariumsGenereVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class TerrariumGenereServiceImpl implements TerrariumGenereService {
    @Autowired
    private TerrariumRepositary terrariumRepositary;
    @Autowired
    private InterrupteurRepository interrupteurRepository;
    @Autowired
    private InterrupteurService interrupteurService;
    @Autowired
    private AlarmeRepository alarmeRepository;

    @Autowired
    private TerrariumService terrariumService;
    @Autowired
    private ChauffageRepository chauffageRepositary;
    @Autowired
    private LumiereRepository lumiereRepositary;

    @Autowired
    private PulverisationRepository pulverisationRepository;

    public MaxminVO getMaxMinChauffage(){
        MaxminVO maxminVO=new MaxminVO();
        Terrarium terrarium_current = terrariumService.getCurrentParameter();
        Date currentTime = terrarium_current.getCreateTime();
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentTime);
        int month = cal.get(Calendar.MONTH) + 1;
        int heure = cal.get(Calendar.HOUR_OF_DAY);
        double currentTemperature = terrarium_current.getTemperature();
        List<Chauffage> chauffages = chauffageRepositary.findAll();
        if (chauffages.size() == 0) {

            maxminVO=new MaxminVO(-1,-1);

        } else {
            Chauffage chauffageConfigurationCourant = new Chauffage();
            boolean isChauffageConfiguration = false;
            for (Chauffage c : chauffages) {
                if (c.getMoisDebut() <= month && c.getMoisFin() >= month && c.getHeureDebut() <= heure && heure <= c.getHeureFin()) {
                    chauffageConfigurationCourant = c;
                    isChauffageConfiguration = true;
                    break;
                }
            }
            if (isChauffageConfiguration) {
                maxminVO=new MaxminVO(chauffageConfigurationCourant.getMax(),chauffageConfigurationCourant.getMin());

            }
            else{
                maxminVO=new MaxminVO(-1,-1);

            }


        }



        return maxminVO;
    }

    public MaxminVO getMaxMinMoisChauffage(){
        MaxminVO maxminVO=new MaxminVO();
        Terrarium terrarium_current = terrariumService.getCurrentParameter();
        Date currentTime = terrarium_current.getCreateTime();
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentTime);
        int month = cal.get(Calendar.MONTH) + 1;
        int heure = cal.get(Calendar.HOUR_OF_DAY);
        double currentTemperature = terrarium_current.getTemperature();
        List<Chauffage> chauffages = chauffageRepositary.findAll();
        if (chauffages.size() == 0) {

            maxminVO=new MaxminVO(-1,-1);

        } else {
            Chauffage chauffageConfigurationCourant = new Chauffage();
            boolean isChauffageConfiguration = false;
            for (Chauffage c : chauffages) {
                if (c.getMoisDebut() <= month && c.getMoisFin() >= month) {
                    chauffageConfigurationCourant = c;
                    isChauffageConfiguration = true;
                    break;
                }
            }
            if (isChauffageConfiguration) {
                maxminVO=new MaxminVO(chauffageConfigurationCourant.getMax(),chauffageConfigurationCourant.getMin());

            }
            else{
                maxminVO=new MaxminVO(-1,-1);

            }


        }



        return maxminVO;


    }

    public MaxminVO getMaxMinPulverisation(){
        MaxminVO maxminVO=new MaxminVO();
        Terrarium terrarium_current = terrariumRepositary.getCurrentParameter().get(0);

        if (!pulverisationRepository.findByMode("hygrometrie").isPresent()) {
            maxminVO=new MaxminVO(-1,-1);

        } else {
            List<Pulverisation> pulverisationList = pulverisationRepository.findByMode("hygrometrie").get();
            Date currentTime = terrarium_current.getCreateTime();
            Calendar cal = Calendar.getInstance();
            cal.setTime(currentTime);
            int month = cal.get(Calendar.MONTH) + 1;
            int heure = cal.get(Calendar.HOUR_OF_DAY);
            Pulverisation pulverisation = new Pulverisation();
            boolean isConfiguration = false;
            for (Pulverisation p : pulverisationList) {
                if (p.getMoisDebut() <= month && p.getMoisFin() >= month && p.getHeureDebut() <= heure && heure <= p.getHeureFin()) {
                    pulverisation = p;
                    isConfiguration = true;
                    break;
                }
            }
            if (isConfiguration) {
                maxminVO=new MaxminVO(pulverisation.getTaux_hygrometrie_max(),pulverisation.getTaux_hygrometrie_min());

            }
            else {
                maxminVO=new MaxminVO(-1,-1);

            }

        }

        return maxminVO;
    }

    public MaxminVO getMaxMinMoisPulverisation(){
        MaxminVO maxminVO=new MaxminVO();
        Terrarium terrarium_current = terrariumRepositary.getCurrentParameter().get(0);

        if (!pulverisationRepository.findByMode("hygrometrie").isPresent()) {
            maxminVO=new MaxminVO(-1,-1);

        } else {
            List<Pulverisation> pulverisationList = pulverisationRepository.findByMode("hygrometrie").get();
            Date currentTime = terrarium_current.getCreateTime();
            Calendar cal = Calendar.getInstance();
            cal.setTime(currentTime);
            int month = cal.get(Calendar.MONTH) + 1;
            int heure = cal.get(Calendar.HOUR_OF_DAY);
            Pulverisation pulverisation = new Pulverisation();
            boolean isConfiguration = false;
            for (Pulverisation p : pulverisationList) {
                if (p.getMoisDebut() <= month && p.getMoisFin() >= month ) {
                    pulverisation = p;
                    isConfiguration = true;
                    break;
                }
            }
            if (isConfiguration) {
                maxminVO=new MaxminVO(pulverisation.getTaux_hygrometrie_max(),pulverisation.getTaux_hygrometrie_min());

            }
            else {
                maxminVO=new MaxminVO(-1,-1);

            }

        }

        return maxminVO;
    }
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
        // Terrarium hum_max = terrariumRepositary.findMaxHumidites(quantite);
//        TerrariumGenereVO humiditeVO_max = new TerrariumGenereVO();
//        humiditeVO_max.setT(hum_max.getCreateTime());
//        humiditeVO_max.setY(hum_max.getHumidite());
//
//
        // Terrarium hum_min = terrariumRepositary.findMinHumidites(quantite);
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
        Optional<Alarme> humiditeAlarmeOptional=alarmeRepository.findByType("hygrometrie");
//        if(humiditeAlarmeOptional.isPresent()){
//            Alarme alarme=humiditeAlarmeOptional.get();
//            humiditesVO.setMax(alarme.getMax());
//            humiditesVO.setMin(alarme.getMin());
//        }
//        else{
//            humiditesVO.setMax(-1);
//            humiditesVO.setMin(-1);
//
//        }
            humiditesVO.setMax(this.getMaxMinPulverisation().getMax());
            humiditesVO.setMin(this.getMaxMinPulverisation().getMin());
        humiditesVO.setSymbol("%");
        humiditesVO.setValues(humiditeVOList);
        humiditesVO.setId(2);
        humiditesVO.setName("Humidite");
        humiditesVO.setProgName("pulverisation");


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
     //   Terrarium temp_max = terrariumRepositary.findMaxTemperatures(quantite);
//        TerrariumGenereVO tempVO_max = new TerrariumGenereVO();
//        tempVO_max.setT(temp_max.getCreateTime());
//        tempVO_max.setY(temp_max.getTemperature());
//
//
//
  //      Terrarium temp_min = terrariumRepositary.findMinTemperatures(quantite);
//        TerrariumGenereVO tempVO_min = new TerrariumGenereVO();
//        tempVO_min.setT(temp_min.getCreateTime());
//        tempVO_min.setY(temp_min.getTemperature());



        TerrariumsGenereVO temperaturesVO=new TerrariumsGenereVO();

        temperaturesVO.setSymbol("°C");
        Interrupteur chauffage=interrupteurService.getControleInterrupteur("chauffage");
        Optional<Alarme> temperatureAlarmeOptional=alarmeRepository.findByType("temperature");
//        if(temperatureAlarmeOptional.isPresent()){
//           Alarme alarme=temperatureAlarmeOptional.get();
//           temperaturesVO.setMax(alarme.getMax());
//           temperaturesVO.setMin(alarme.getMin());
//        }
//        else{
//            temperaturesVO.setMax(-1);
//            temperaturesVO.setMin(-1);
//
//        }
        temperaturesVO.setMax(this.getMaxMinChauffage().getMax());
        temperaturesVO.setMin(this.getMaxMinChauffage().getMin());
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
        temperaturesVO.setProgName("chauffage");
        return temperaturesVO;

    }



    public List<Terrarium> getDonneeUneSemaine(int quantite) {
        List<Terrarium> terrariums=terrariumRepositary.donneUneSemaine();

        List<Terrarium> newTerrariums=new ArrayList<Terrarium>();
        int distance=terrariums.size()/quantite;
        for(int i=0;i<terrariums.size();i=i+distance){
            Terrarium t=terrariums.get(i);
            newTerrariums.add(t);

        }
        int size=newTerrariums.size();
        return newTerrariums.subList(size-quantite, size);}

    public TerrariumsGenereVO GetUneSemaineTemperaturesVO(int quantite) {
        List<Terrarium> terraiumList = this.getDonneeUneSemaine(quantite);
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
        Optional<Alarme> temperatureAlarmeOptional=alarmeRepository.findByType("temperature");
//        if(temperatureAlarmeOptional.isPresent()){
//            Alarme alarme=temperatureAlarmeOptional.get();
//            temperaturesVO.setMax(alarme.getMax());
//            temperaturesVO.setMin(alarme.getMin());
//        }
//        else{
//            temperaturesVO.setMax(-1);
//            temperaturesVO.setMin(-1);
//
//        }
        temperaturesVO.setMax(this.getMaxMinMoisChauffage().getMax());
        temperaturesVO.setMin(this.getMaxMinMoisChauffage().getMin());
        temperaturesVO.setValues(terraiumListVO);
        temperaturesVO.setId(1);
        temperaturesVO.setName("Temperature");
        temperaturesVO.setProgName("chauffage");
        return temperaturesVO;

    }

    public TerrariumsGenereVO GetUneSemaineHumiditesVO(int quantite) {

        List<Terrarium> terraiumList = this.getDonneeUneSemaine(quantite);
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
        Optional<Alarme> humiditeAlarmeOptional=alarmeRepository.findByType("hygrometrie");
//        if(humiditeAlarmeOptional.isPresent()){
//            Alarme alarme=humiditeAlarmeOptional.get();
//            humiditesVO.setMax(alarme.getMax());
//            humiditesVO.setMin(alarme.getMin());
//        }
//        else{
//            humiditesVO.setMax(-1);
//            humiditesVO.setMin(-1);
//
//        }
        humiditesVO.setMax(this.getMaxMinMoisPulverisation().getMax());
        humiditesVO.setMin(this.getMaxMinMoisPulverisation().getMin());
        humiditesVO.setSymbol("%");
        humiditesVO.setValues(humiditeVOList);
        humiditesVO.setId(2);
        humiditesVO.setName("Humidite");
        humiditesVO.setProgName("pulverisation");

        return humiditesVO;

    }

    public List<Terrarium> getDonneeUnMois(int quantite) {
        List<Terrarium> terrariums=terrariumRepositary.donneUnMois();
        List<Terrarium> newTerrariums=new ArrayList<Terrarium>();
        int distance=terrariums.size()/quantite;
        for(int i=0;i<terrariums.size();i=i+distance){
            Terrarium t=terrariums.get(i);
            newTerrariums.add(t);

        }
        int size=newTerrariums.size();
        return newTerrariums.subList(size-quantite, size);}

    public TerrariumsGenereVO GetUnMoisTemperaturesVO(int quantite) {
        List<Terrarium> terraiumList = this.getDonneeUnMois(quantite);
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
 //       Optional<Alarme> temperatureAlarmeOptional=alarmeRepository.findByType("temperature");
//        if(temperatureAlarmeOptional.isPresent()){
//            Alarme alarme=temperatureAlarmeOptional.get();
//            temperaturesVO.setMax(alarme.getMax());
//            temperaturesVO.setMin(alarme.getMin());
//        }
//        else{
//            temperaturesVO.setMax(-1);
//            temperaturesVO.setMin(-1);
//
//        }
        temperaturesVO.setMax(this.getMaxMinMoisChauffage().getMax());
        temperaturesVO.setMin(this.getMaxMinMoisChauffage().getMin());
        temperaturesVO.setProgName("chauffage");
        temperaturesVO.setValues(terraiumListVO);
        temperaturesVO.setId(1);
        temperaturesVO.setName("Temperature");

        return temperaturesVO;

    }

    public TerrariumsGenereVO GetUnMoisHumiditesVO(int quantite) {

        List<Terrarium> terraiumList = this.getDonneeUnMois(quantite);
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
        Optional<Alarme> humiditeAlarmeOptional=alarmeRepository.findByType("hygrometrie");
//        if(humiditeAlarmeOptional.isPresent()){
////            Alarme alarme=humiditeAlarmeOptional.get();
////            humiditesVO.setMax(alarme.getMax());
////            humiditesVO.setMin(alarme.getMin());
////        }
////        else{
////            humiditesVO.setMax(-1);
////            humiditesVO.setMin(-1);
////
////        }
        humiditesVO.setMax(this.getMaxMinMoisPulverisation().getMax());
        humiditesVO.setMin(this.getMaxMinMoisPulverisation().getMin());
        humiditesVO.setSymbol("%");
        humiditesVO.setValues(humiditeVOList);
        humiditesVO.setId(2);
        humiditesVO.setName("Humidite");
        humiditesVO.setProgName("pulverisation");

        return humiditesVO;

    }





}
