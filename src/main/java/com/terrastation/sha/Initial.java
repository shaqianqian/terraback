package com.terrastation.sha;

import com.terrastation.sha.Controller.TerrariumController;
import com.terrastation.sha.Entity.Pulverisation;
import com.terrastation.sha.Entity.Pulverisationheure;
import com.terrastation.sha.Entity.Terrarium;
import com.terrastation.sha.Repositary.PulverisationHeureRepository;
import com.terrastation.sha.Repositary.PulverisationRepository;
import com.terrastation.sha.Repositary.TerrariumRepositary;
import com.terrastation.sha.Service.DynamicTaskService;
import com.terrastation.sha.Service.InterrupteurService;
import com.terrastation.sha.Service.ScheduledForDynamicCron;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.List;

/**
 * La methode fonctionne quand le projet lance
 */
@Component
public class Initial implements CommandLineRunner {

    Logger log = LoggerFactory.getLogger(TerrariumController.class);
    @Autowired
    private InterrupteurService interrupteurService;
    @Autowired
    private TerrariumRepositary terrariumRepositary;
    @Autowired
    private PulverisationHeureRepository pulverisationHeureRepository;
    @Autowired
    private PulverisationRepository pulverisationRepository;
    @Autowired
    private DynamicTaskService dynamicTaskService;


    @Override
    public void run(String... args) throws Exception {

        if (pulverisationRepository.findAll().isEmpty()){
            System.out.println("vous avez pas encore configurez la pulverisation");

        }
        else if(pulverisationRepository.findAll().get(0).getPulverisationheure().size()==0){

            System.out.println("vous avez pas encore configurez la pulverisation");
        }
        else {
            Pulverisation pulverisation = pulverisationRepository.findAll().get(0);
            if (pulverisation.getMode().equals("horaire")) {
                String moi = pulverisation.getMoisDebut() + "-" + pulverisation.getMoisFin();
                String heures = pulverisation.getPulverisationheure().get(0).getHeure() + "";
                Calendar c = Calendar.getInstance();
                int heureCurrent = c.get(Calendar.HOUR_OF_DAY);
                int dureeCorrespendant = pulverisation.getPulverisationheure().get(0).getDuree();
                if (pulverisation.getPulverisationheure().size() > 1) {
                    for (int i = 1; i < pulverisation.getPulverisationheure().size(); i++) {

                        heures = heures + "," + pulverisation.getPulverisationheure().get(i).getHeure();
                        if (pulverisation.getPulverisationheure().get(i).getHeure() == heureCurrent) {

                            dureeCorrespendant = pulverisation.getPulverisationheure().get(i).getDuree();
                        }

                    }
                }
                String cron = MessageFormat.format("0 * {0} ? {1} ?", heures, moi);
                System.out.println(cron);
                dynamicTaskService.startCron(cron, dureeCorrespendant);
            }
//        scheduledForDynamicCron.setCron("* 0 * * * ?");
            //scheduledForDynamicCron.setCron("0 0 * * * ?");
        }
    }
}
