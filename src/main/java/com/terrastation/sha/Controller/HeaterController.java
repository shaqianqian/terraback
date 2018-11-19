package com.terrastation.sha.Controller;

import com.terrastation.sha.Entity.Chauffage;
import com.terrastation.sha.Entity.Heater;
import com.terrastation.sha.Entity.Terrarium;
import com.terrastation.sha.Repositary.ChauffageRepository;
import com.terrastation.sha.Repositary.HeaterRepository;
import com.terrastation.sha.Repositary.TerrariumRepositary;
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
    private HeaterRepository heaterRepository;
    @Autowired
    private TerrariumRepositary temrraiumRepository;


    //get l'etat de chauffage
    @RequestMapping(value = "/terrarium/chauffage/getEtatChauffage", method = RequestMethod.GET)


    public ResultVO<Heater> changeEtatPieceVO() {
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
        Terrarium terraium_current = temrraiumRepository.findCurrentParametre();
        Date currentTime = terraium_current.getCreateTime();
        double currentTemperature = terraium_current.getTemperature();
       log.info(currentTemperature+"");
        List<Chauffage> chauffages = chauffageRepository.findAll();
        for (Chauffage c : chauffages) {
            if (c.getDateDebut().before(currentTime) && c.getDateFin().after(currentTime)) {
                log.info("max_limite_temperautre "+c.getMax());
                log.info("min_limite_temperautre "+c.getMin());
                if (c.getMax() <currentTemperature) {
                    heater.setEtat(false);
                    heaterRepository.save(heater);
                    log.info("trop chaud, eteindre le chauffage");
                } else if (c.getMin()>currentTemperature) {
                    heater.setEtat(true);
                    heaterRepository.save(heater);
                    log.info("trop froid, offrir le chauffage");
                }else if(currentTemperature<=c.getMax()&&currentTemperature>=c.getMin()){
                    log.info("change pas letat de chauffage");
                    heaterRepository.save(heater);
                }

            }

        }


        return ResultUtil.success(heater);

    }


}