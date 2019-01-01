package com.terrastation.sha.Controller;

import com.terrastation.sha.Entity.Interrupteur;
import com.terrastation.sha.Entity.Pulverisation;
import com.terrastation.sha.Entity.Terrarium;
import com.terrastation.sha.Enums.ResultEnum;
import com.terrastation.sha.Exception.IdNotExistException;
import com.terrastation.sha.Repositary.ChauffageRepository;
import com.terrastation.sha.Repositary.InterrupteurRepository;
import com.terrastation.sha.Repositary.TerrariumRepositary;
import com.terrastation.sha.Service.InterrupteurService;
import com.terrastation.sha.Util.ResultUtil;
import com.terrastation.sha.VO.ResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
public class InterrupteurController {
    Logger log = LoggerFactory.getLogger(TerrariumController.class);

    @Autowired
    private InterrupteurService interrupteurService;
    @Autowired
    private InterrupteurRepository interrupteurRepository;


//    //get l'etat de chauffage
//    @RequestMapping(value = "/terrarium/chauffage/getEtatChauffage", method = RequestMethod.GET)
//
//
//    public ResultVO<Interrupteur> getEtatInterrupterProgrammable() {
//        Interrupteur interrupteur= interrupteurService.getControleInterrupteur("chauffage");
//        return ResultUtil.success(interrupteur);
//
//    }
//    //change le facon de controler le interrupteur
//    @RequestMapping(value = "/terrarium/chauffage/changeControleInterrupteur", method = RequestMethod.POST)
//    public ResultVO<Interrupteur> changeControleInterrupteur( @RequestParam("isProg") boolean isProg) {
//
//        Interrupteur newInterrupteur=interrupteurService.changeControleInterrupteur("chauffage",isProg);
//
//        return ResultUtil.success(newInterrupteur);
//
//    }




}