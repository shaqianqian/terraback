package com.terrastation.sha.Controller;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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



    @RequestMapping(value = "/add", method = RequestMethod.POST)

    public ResultVO<Lumiere> add(@RequestParam String email) {
        Profil profil=new Profil();
        profil.setEmail(email);
        return  ResultUtil.success(profilRepository.save(profil));

    }


    @RequestMapping(value = "/sendEmail", method = RequestMethod.GET)

    public ResultVO sendEmail() {
        alarmeService.send();
        return ResultUtil.success("fini");
    }


    @RequestMapping(value = "/test", method = RequestMethod.GET)

    public ResultVO test() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       Date current=terrariumService.getCurrentParameter().getCreateTime();
       String currentString=df.format(current);

       Date debut=new Date();
       debut.setTime(current.getTime()-10000);

        String debutString=df.format(debut);
      List<Terrarium> terrariumList=terrariumRepositary.variation(debutString,currentString);
        return ResultUtil.success(terrariumList);
    }






}
