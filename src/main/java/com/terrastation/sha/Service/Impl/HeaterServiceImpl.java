package com.terrastation.sha.Service.Impl;

import com.terrastation.sha.Entity.Chauffage;
import com.terrastation.sha.Entity.Heater;
import com.terrastation.sha.Entity.Terrarium;
import com.terrastation.sha.Repositary.HeaterRepository;
import com.terrastation.sha.Repositary.ChauffageRepository;
import com.terrastation.sha.Repositary.TerrariumRepositary;
import com.terrastation.sha.Service.HeaterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class HeaterServiceImpl implements HeaterService {
    @Autowired
    private HeaterRepository heaterRepository;

    @Autowired
    private TerrariumRepositary terrariumRepositary;
    @Autowired
    private ChauffageRepository chauffageRepositary;

    public Heater changeEtatPieceVO() {
        Heater heater;
        if (heaterRepository.findAll().size() == 0) {
            Heater newHeater = new Heater();
            newHeater.setEtat(false);
            newHeater.setType("chauffage");
            heater = heaterRepository.save(newHeater);

        } else {
            heater = heaterRepository.findAll().get(0);
        }
        Boolean etat = heater.isEtat();
        log.info("heater etat maintenant est "+etat);
        Terrarium terraium_current = terrariumRepositary.findCurrentParametre();
        Date currentTime = terraium_current.getCreateTime();
        Calendar cal=Calendar.getInstance();
        cal.setTime(currentTime);
        int month=cal.get(Calendar.MONTH)+1;
//        log.info("月份"+month);
        int heure=cal.get(Calendar.HOUR)+12;
//        log.info("月份 "+month+" 小时 "+heure);
        double currentTemperature = terraium_current.getTemperature();
        log.info(currentTemperature+"");
        List<Chauffage> chauffages = chauffageRepositary.findAll();
        for (Chauffage c : chauffages) {

            if(c.getDateDebut()<=month&&c.getDateFin()>=month&&c.getHeureDebut()<=heure&&heure<=c.getHeureFin())
            {
                log.info("max_limite_temperautre "+c.getMax());
                log.info("min_limite_temperautre "+c.getMin());
                if (c.getMax() <currentTemperature) {
                    if(etat)
                    {heater.setEtat(false);
                    heaterRepository.save(heater);
                    log.info("trop chaud, eteindre le chauffage");}
                    else{log.info("change pas letat de chauffage");}
                } else if (c.getMin()>currentTemperature) {
                    if(!etat)
                    {heater.setEtat(true);
                    heaterRepository.save(heater);
                    log.info("trop froid, offrir le chauffage");}
                    else{log.info("change pas letat de chauffage");}
                }else if(currentTemperature<=c.getMax()&&currentTemperature>=c.getMin()){
                    log.info("change pas letat de chauffage");
                }


            }

//            if (c.getDateDebut().before(currentTime) && c.getDateFin().after(currentTime)) {
//                log.info("max_limite_temperautre "+c.getMax());
//                log.info("min_limite_temperautre "+c.getMin());
//                if (c.getMax() <currentTemperature) {
//                    if(etat)
//                    {heater.setEtat(false);
//                    heaterRepository.save(heater);
//                    log.info("trop chaud, eteindre le chauffage");}
//                    else{log.info("change pas letat de chauffage");}
//                } else if (c.getMin()>currentTemperature) {
//                    if(!etat)
//                    {heater.setEtat(true);
//                    heaterRepository.save(heater);
//                    log.info("trop froid, offrir le chauffage");}
//                    else{log.info("change pas letat de chauffage");}
//                }else if(currentTemperature<=c.getMax()&&currentTemperature>=c.getMin()){
//                    log.info("change pas letat de chauffage");
////                    heaterRepository.save(heater);
//                }
//
//            }

        }


        return heater;

    }

}
