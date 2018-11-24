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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
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
        log.info("L'etat du chauffage courant est "+etat);
        Terrarium terraium_current = terrariumRepositary.findCurrentParametre();
        Date currentTime = terraium_current.getCreateTime();
        Calendar cal=Calendar.getInstance();
        cal.setTime(currentTime);
        int month=cal.get(Calendar.MONTH)+1;
//        log.info("月份"+month);
        int heure=cal.get(Calendar.HOUR)+12;
//        log.info("月份 "+month+" 小时 "+heure);
        double currentTemperature = terraium_current.getTemperature();
        log.info("La temperature courante est :"+currentTemperature);
        List<Chauffage> chauffages = chauffageRepositary.findAll();
        for (Chauffage c : chauffages) {

            if(c.getMoisDebut()<=month&&c.getMoisFin()>=month&&c.getHeureDebut()<=heure&&heure<=c.getHeureFin())
            {
                log.info("Le MAXIMUM de temperautre est "+c.getMax());
                log.info("Le MINIMUM de temperautre est "+c.getMin());
                if (c.getMax() <currentTemperature) {
                    if(etat)
                    {heater.setEtat(false);
                    heaterRepository.save(heater);
                        try{
                            log.info("START : Lancer le script du chauffage pour l'eteindre");
                            Process pr = Runtime.getRuntime().exec("python ../python/chauffage_test.py 0");

                            BufferedReader in = new BufferedReader(new
                                    InputStreamReader(pr.getInputStream()));
                            String line;
                            while ((line = in.readLine()) != null) {
                                System.out.println(line);
                            }
                            in.close();
                            pr.waitFor();
                            log.info("END : On a reussi à l'eteindre");
                        } catch (Exception e){
                            e.printStackTrace();
                        }

//                    log.info("trop chaud, eteindre le chauffage");

                    }
                    else{
                        log.info("Le chauffage est deja fermé, on ne change pas l'état du chauffage");}

                } else if (c.getMin()>currentTemperature) {
                    if(!etat)
                    {heater.setEtat(true);
                    heaterRepository.save(heater);
                    log.info("Trop froid, offrir le chauffage");

                        try{
                            log.info("START : Lancer le script de chauffage pour ouvrir le chauffage");
                            Process pr = Runtime.getRuntime().exec("python ../python/chauffage_test.py 1");

                            BufferedReader in = new BufferedReader(new
                                    InputStreamReader(pr.getInputStream()));
                            String line;
                            while ((line = in.readLine()) != null) {
                                System.out.println(line);
                            }
                            in.close();
                            pr.waitFor();
                            log.info("END : on a reussi à ouvrir le chauffage");
                        } catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                    else{log.info("Le chauffage est deja ouvert, on ne change pas l'etat du chauffage");}
                }else if(currentTemperature<=c.getMax()&&currentTemperature>=c.getMin()){
                    log.info("La temperature a l'air correcte manintenant, on ne change pas l'etat du chauffage");
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
