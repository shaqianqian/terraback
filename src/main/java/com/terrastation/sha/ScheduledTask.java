package com.terrastation.sha;

import com.terrastation.sha.Controller.TerrariumController;
import com.terrastation.sha.Entity.Terrarium;
import com.terrastation.sha.Repositary.TerrariumRepositary;
import com.terrastation.sha.Service.InterrupteurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask {
    Logger log = LoggerFactory.getLogger(TerrariumController.class);
    @Autowired
    private InterrupteurService interrupteurService;
    @Autowired
    private TerrariumRepositary terrariumRepositary;

    @Scheduled(fixedRate = 30000)
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

    }


}