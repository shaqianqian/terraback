package com.terrastation.sha;

import com.terrastation.sha.Controller.TerrariumController;
import com.terrastation.sha.Entity.Pulverisation;
import com.terrastation.sha.Entity.Terrarium;
import com.terrastation.sha.Repositary.PulverisationHeureRepository;
import com.terrastation.sha.Repositary.PulverisationRepository;
import com.terrastation.sha.Repositary.TerrariumRepositary;
import com.terrastation.sha.Service.InterrupteurService;
import com.terrastation.sha.Service.PulverisationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Component
public class ScheduledTask {
    Logger log = LoggerFactory.getLogger(TerrariumController.class);
    @Autowired
    private InterrupteurService interrupteurService;
    @Autowired
    private TerrariumRepositary terrariumRepositary;
    @Autowired
    private PulverisationRepository pulverisationRepository;
    @Autowired
    private PulverisationService pulverisationService;


    @Scheduled(fixedRate = 30000, initialDelayString = "10000")
    //30s une fois
    public void reportCurrentTime() {
        Terrarium terrarium_current = terrariumRepositary.getCurrentParameter();
        if (interrupteurService.getControleInterrupteur("chauffage").isProg()) {
            interrupteurService.InterrupterProgrammableChauffage("chauffage");
        } else {
            interrupteurService.InterrupterManuelleChauffage("chauffage");
        }

        if (interrupteurService.getControleInterrupteur("lumiere").isProg()) {
            interrupteurService.InterrupterProgrammableLumiere("lumiere");
        } else {
            interrupteurService.InterrupterManuelleLumiere("lumiere");
        }

        if (pulverisationRepository.findAll().isEmpty()) {
            log.info("vous avez pas encore configurez la pulverisation");

        }
//        else if(pulverisationRepository.findAll().get(0).getPulverisationheure().size()==0){
//
//          log.info("vous avez pas encore configurez la pulverisation");
//        }
        else {

          pulverisationService.pulverisationModeHygrometrie();

        }

    }


}