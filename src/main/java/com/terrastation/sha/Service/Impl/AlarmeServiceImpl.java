package com.terrastation.sha.Service.Impl;


import com.terrastation.sha.Controller.AlarmeController;
import com.terrastation.sha.Entity.Alarme;
import com.terrastation.sha.Entity.Terrarium;
import com.terrastation.sha.Repositary.AlarmeRepository;
import com.terrastation.sha.Repositary.ProfilRepository;
import com.terrastation.sha.Repositary.TerrariumRepositary;
import com.terrastation.sha.Service.AlarmeService;
import com.terrastation.sha.Service.TerrariumService;
import com.terrastation.sha.Util.ResultUtil;
import com.terrastation.sha.VO.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class AlarmeServiceImpl implements AlarmeService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ProfilRepository profilRepository;

    @Autowired
    private TerrariumRepositary terrariumRepositary;

    @Autowired
    private AlarmeRepository alarmeRepository;

    @Autowired
    private AlarmeService alarmeService;
    @Autowired
    private TerrariumService terrariumService;

    Logger log = LoggerFactory.getLogger(AlarmeController.class);


    public void send(){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("terrastation111@gmail.com");
        message.setTo(profilRepository.findAll().get(0).getEmail());
        message.setSubject("Terrastation");
        message.setText("c'est un Email de terrastation, hahaha je suis en train de tester la systeme de l'alarme");
        mailSender.send(message);

    }
     public List<Terrarium> alarmeTemperature() {
         DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         Terrarium terrarium = terrariumService.getCurrentParameter();
         Date current = terrarium.getCreateTime();
         String currentString = df.format(current);
         Alarme alarme = alarmeRepository.findByType("temperature").get();

         Date debut = new Date();

         debut.setTime(new Double(current.getTime() - 60000 * alarme.getVariation()).longValue());


         log.info("debut is " + debut.toString());
         String debutString = df.format(debut);


         List<Terrarium> terrariumList = terrariumRepositary.variation(debutString, currentString);
         sortListByTemperature(terrariumList);

         double max_Temperature = terrariumList.get(terrariumList.size() - 1).getTemperature();
         log.info("max_temperature is " + max_Temperature);
         double min_Temperature = terrariumList.get(0).getTemperature();
         log.info("min_temperature is " + min_Temperature);
         if (max_Temperature - min_Temperature > alarme.getVariation() || terrarium.getTemperature() > alarme.getMax() || terrarium.getTemperature() < alarme.getMin()) {
             alarmeService.send();
             log.info("send a email" + alarme.getMessage());
         }
         return terrariumList;


     }

    public List<Terrarium> alarmeHygrometrie() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Terrarium terrarium = terrariumService.getCurrentParameter();
        Date current = terrarium.getCreateTime();
        String currentString = df.format(current);
        Alarme alarme = alarmeRepository.findByType("hygrometrie").get();
        Date debut = new Date();
        debut.setTime(new Double(current.getTime() - 60000 * alarme.getVariation()).longValue());
        log.info("debut is " + debut.toString());
        String debutString = df.format(debut);
        List<Terrarium> terrariumList = terrariumRepositary.variation(debutString, currentString);
        sortListByHumidity(terrariumList);

        double max_humidity = terrariumList.get(terrariumList.size() - 1).getHumidite();
        log.info("max_humidity is " + max_humidity);
        double min_humidity = terrariumList.get(0).getHumidite();
        log.info("min_humidity is " + min_humidity);
        if (max_humidity - min_humidity > alarme.getVariation() || terrarium.getHumidite() > alarme.getMax() || terrarium.getHumidite() < alarme.getMin()) {
            alarmeService.send();
            log.info("send a email" + alarme.getMessage());
        }
        return terrariumList;
    }


    private void sortListByTemperature(List<Terrarium> list) {
        Collections.sort(list, new Comparator<Terrarium>() {
            @Override
            public int compare(Terrarium o1, Terrarium o2) {
                if (o1.getTemperature() > o2.getTemperature()) {
                    return 1;
                } else if (o1.getTemperature() < o2.getTemperature()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
    }

    private void sortListByHumidity(List<Terrarium> list) {
        Collections.sort(list, new Comparator<Terrarium>() {
            @Override
            public int compare(Terrarium o1, Terrarium o2) {
                if (o1.getHumidite() > o2.getHumidite()) {
                    return 1;
                } else if (o1.getHumidite() < o2.getHumidite()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
    }

}