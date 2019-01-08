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

    @RequestMapping(value = "/sendEmailTemperature", method = RequestMethod.GET)

    public ResultVO alarmeTemperature() {
        return ResultUtil.success(alarmeService.alarmeTemperature());
    }


    @RequestMapping(value = "/sendEmailHygrometrie", method = RequestMethod.GET)

    public ResultVO alarmeHygrometrie() {

        return ResultUtil.success(alarmeService.alarmeHygrometrie());
    }




}
