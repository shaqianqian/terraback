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
import java.util.*;

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

    /**
     * Send an information alert to the user
     */
    public void send(String Notification) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("terrastation111@gmail.com");
        message.setTo("sqq940814@gmail.com");
        message.setSubject("Terrastation");
        message.setText(Notification);
        mailSender.send(message);

    }
    /**
     * Send an information alert to the user
     * @return
     */
    public List<Terrarium> alarmeTemperature() {

        Optional<Alarme> alarmeOptional = alarmeRepository.findByType("temperature");
        if (alarmeOptional.isPresent()) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Terrarium terrarium = terrariumService.getCurrentParameter();
            Date current = terrarium.getCreateTime();
            String currentString = df.format(current);
            Alarme alarme = alarmeOptional.get();

            Date debut = new Date();

            debut.setTime(new Double(current.getTime() - 60000 * alarme.getVariation()).longValue());
            log.info("debut is " + debut.toString());
            String debutString = df.format(debut);
            List<Terrarium> terrariumList = terrariumRepositary.variation(debutString, currentString);
            sortListByTemperature(terrariumList);

            double max_Temperature = terrariumList.get(terrariumList.size() - 1).getTemperature();
          //  log.info("max_temperature is " + max_Temperature);
            double min_Temperature = terrariumList.get(0).getTemperature();
           // log.info("min_temperature is " + min_Temperature);
            if (max_Temperature - min_Temperature > alarme.getVariation()) {
                this.send(alarme.getMessage()+"La temperature change trop vite," );
                log.info("send a email" + alarme.getMessage());
            } else if (terrarium.getTemperature() > alarme.getMax()) {
                this.send(alarme.getMessage()+"La temperature est trop chaud," );
                log.info("send a email" + alarme.getMessage());
            } else if (terrarium.getTemperature() < alarme.getMin()) {
                this.send(alarme.getMessage()+"La temperature est trop froid," );
                log.info("send a email" + alarme.getMessage());

            }
            return terrariumList;

        } else {

            return null;
        }

    }

    /**
     * Send an information alert to the user
     * @return
     */
    public List<Terrarium> alarmeHygrometrie() {
        Optional<Alarme> alarmeOptional = alarmeRepository.findByType("hygrometrie");
        if (alarmeOptional.isPresent()) {
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
          //  log.info("max_humidity is " + max_humidity);
            double min_humidity = terrariumList.get(0).getHumidite();
           // log.info("min_humidity is " + min_humidity);
            if (max_humidity - min_humidity > alarme.getVariation()) {
                this.send("L'humidite change trop vite," + alarme.getMessage());
                log.info("send a email" + alarme.getMessage());
            } else if (terrarium.getHumidite() > alarme.getMax()) {
                this.send("Le taux de hygrometrie est trop eleve," + alarme.getMessage());
                log.info("send a email" + alarme.getMessage());
            } else if (terrarium.getHumidite() < alarme.getMin()) {
                this.send("Le taux de hygrometrie est trop bas," + alarme.getMessage());
                log.info("send a email" + alarme.getMessage());

            }
            return terrariumList;
        } else {
            return null;
        }
    }

    /**
     *Sort the temperature from small to large
     * @param list
     */
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

    /**
     * Sort the air humidity from small to large
     * @param list
     */
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
