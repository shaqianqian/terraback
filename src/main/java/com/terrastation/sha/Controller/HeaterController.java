package com.terrastation.sha.Controller;

import com.terrastation.sha.Entity.Chauffage;
import com.terrastation.sha.Entity.Heater;
import com.terrastation.sha.Entity.Terrarium;
import com.terrastation.sha.Repositary.ChauffageRepository;
import com.terrastation.sha.Repositary.HeaterRepository;
import com.terrastation.sha.Repositary.TerrariumRepositary;
import com.terrastation.sha.Service.HeaterService;
import com.terrastation.sha.Util.ResultUtil;
import com.terrastation.sha.VO.ResultVO;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@RestController
public class HeaterController {
    org.slf4j.Logger log = LoggerFactory.getLogger(TerrariumController.class);
    @Autowired
    private ChauffageRepository chauffageRepository;
    @Autowired
    private HeaterService  heaterService;
    @Autowired
    private HeaterRepository heaterRepository;
    @Autowired
    private TerrariumRepositary temrraiumRepository;


    //get l'etat de chauffage
    @RequestMapping(value = "/terrarium/chauffage/getEtatChauffage", method = RequestMethod.GET)


    public ResultVO<Heater> changeEtatPieceVO() {
        Heater heater=heaterService.changeEtatPieceVO();
        return ResultUtil.success(heater);

    }


}