package com.terrastation.sha.Controller;

import com.terrastation.sha.Entity.Alarme;
import com.terrastation.sha.Entity.Lumiere;
import com.terrastation.sha.Entity.Profil;
import com.terrastation.sha.Entity.Terrarium;
import com.terrastation.sha.Repositary.AlarmeRepository;
import com.terrastation.sha.Repositary.ProfilRepository;
import com.terrastation.sha.Repositary.TerrariumRepositary;
import com.terrastation.sha.Service.AlarmeService;
import com.terrastation.sha.Service.TerrariumService;
import com.terrastation.sha.Util.ResultUtil;
import com.terrastation.sha.VO.ResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/terrarium/alarme")
public class AlarmeController {

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

    @RequestMapping(value = "/addProfil", method = RequestMethod.POST)

    public ResultVO<Lumiere> addProgil(@RequestParam String email) {
        Profil profil = new Profil();
        profil.setEmail(email);
        return ResultUtil.success(profilRepository.save(profil));

    }

    @RequestMapping(value = "/addAlarme", method = RequestMethod.POST)

    public ResultVO<Lumiere> addAlarme(@RequestBody Alarme alarme) {

        return ResultUtil.success(alarmeRepository.save(alarme));

    }

//
//    @RequestMapping(value = "/sendEmail", method = RequestMethod.GET)
//
//    public ResultVO sendEmail() {
//        alarmeService.send();
//        return ResultUtil.success("fini");
//    }
//

    @RequestMapping(value = "/sendEmail", method = RequestMethod.GET)

    public ResultVO alarmeTemperature() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Terrarium terrarium=terrariumService.getCurrentParameter();
        Date current = terrarium.getCreateTime();
        String currentString = df.format(current);
        Alarme alarme = alarmeRepository.findByType("temperature").get();

        Date debut = new Date();

        debut.setTime(new Double(current.getTime() - 60000*alarme.getVariation()).longValue());


        log.info("debut is "+debut.toString());
        String debutString = df.format(debut);


        List<Terrarium> terrariumList = terrariumRepositary.variation(debutString, currentString);
        sortListByTemperature(terrariumList);

        double max_Temperature = terrariumList.get(terrariumList.size() - 1).getTemperature();
        log.info("max_temperature is " + max_Temperature);
        double min_Temperature = terrariumList.get(0).getTemperature();
        log.info("min_temperature is " + min_Temperature);
        if (max_Temperature - min_Temperature > alarme.getVariation()||terrarium.getTemperature()>alarme.getMax()||terrarium.getTemperature()<alarme.getMin()) {
            alarmeService.send();
            log.info("send a email" + alarme.getMessage());
        }
        return ResultUtil.success(terrariumList);
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


}
