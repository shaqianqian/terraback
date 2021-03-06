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
import java.util.*;

@CrossOrigin
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

    @RequestMapping(value = "/configEmail", method = RequestMethod.GET)

    public ResultVO <Profil> addProgil(@RequestParam String email) {
        Profil profil = new Profil();
        if(profilRepository.findAll().isEmpty()){
         profil.setEmail(email);


        }
        else{
         profil=profilRepository.findAll().get(0);
         profil.setEmail(email);

        }
        profilRepository.save(profil);
        return ResultUtil.success(profil);

    }
    @RequestMapping(value = "/getEmail", method = RequestMethod.GET)

    public ResultVO<Profil> getProfil() {
        Profil profil = new Profil();
        if(!profilRepository.findAll().isEmpty()){

            profil=profilRepository.findAll().get(0);

        }

        return ResultUtil.success(profil);

    }


    @RequestMapping(value = "/getConfigNotification", method = RequestMethod.GET)

    public ResultVO<List<Alarme>> getConfigNotification() {
        return ResultUtil.success(alarmeRepository.findAll());
    }


    @RequestMapping(value = "/configNotification", method = RequestMethod.POST)

    public ResultVO<List<Alarme>> configNotification(@RequestBody List<Alarme> alarmes) {
        Optional<Alarme> temperatureOptional = alarmeRepository.findByType("temperature");
        if (!temperatureOptional.isPresent()) {
            for (Alarme alarme : alarmes) {
                if (alarme.getType().equals("temperature")) {
                    alarmeRepository.save(alarme);
                    break;
                }

            }

        } else {
           Alarme temperatureAlarme= temperatureOptional.get();
            for (Alarme alarme : alarmes) {
                if (alarme.getType().equals("temperature")) {
                    temperatureAlarme.setDuree(alarme.getDuree());
                    temperatureAlarme.setMax(alarme.getMax());
                    temperatureAlarme.setMin(alarme.getMin());
                    temperatureAlarme.setMessage(alarme.getMessage());
                    temperatureAlarme.setVariation(alarme.getVariation());
                    alarmeRepository.save(temperatureAlarme);
                    break;
                }
            }
        }

        Optional<Alarme> humiditeOptional = alarmeRepository.findByType("hygrometrie");
        if (!humiditeOptional.isPresent()) {
            for (Alarme alarme : alarmes) {
                if (alarme.getType().equals("hygrometrie")) {
                    alarmeRepository.save(alarme);
                    break;
                }
            }
        } else {
            Alarme  humiditeAlarme=  humiditeOptional.get();
            for (Alarme alarme : alarmes) {
                if (alarme.getType().equals("hygrometrie")) {
                    humiditeAlarme.setDuree(alarme.getDuree());
                    humiditeAlarme.setMax(alarme.getMax());
                    humiditeAlarme.setMin(alarme.getMin());
                    humiditeAlarme.setMessage(alarme.getMessage());
                    humiditeAlarme.setVariation(alarme.getVariation());
                    alarmeRepository.save(humiditeAlarme);
                    break;
                }
            }
        }
        return ResultUtil.success(alarmeRepository.findAll());
    }


}
