package com.terrastation.sha;

import com.terrastation.sha.Controller.TerrariumController;
import com.terrastation.sha.Entity.Pulverisation;
import com.terrastation.sha.Repositary.PulverisationHeureRepository;
import com.terrastation.sha.Repositary.PulverisationRepository;
import com.terrastation.sha.Repositary.TerrariumRepositary;
import com.terrastation.sha.Service.DynamicTaskService;
import com.terrastation.sha.Service.InterrupteurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Calendar;

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
            log.info("vous avez pas encore configurez la pulverisation");

        }

        else {
            Pulverisation pulverisation = pulverisationRepository.findAll().get(0);

            if(pulverisation.getMode()==null||pulverisation.getMode().isEmpty()){

                log.info("vous avez pas encore configurez la mode de pulverisation");

            }

            else if (pulverisation.getMode().equals("horaire")) {
                if(pulverisationRepository.findAll().get(0).getPulverisationheure().size()==0){

                    log.info("vous avez pas encore configurez les details de pulverisation en mode horaire ");
                }
                else{
                String moi = pulverisation.getMoisDebut() + "-" + pulverisation.getMoisFin();
                String heures = pulverisation.getPulverisationheure().get(0).getHeure() + "";
                Calendar c = Calendar.getInstance();
                int heureCurrent = c.get(Calendar.HOUR_OF_DAY);
//                log.info("当前时间" + heureCurrent);
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
                dynamicTaskService.startCron(cron);}
            }

//        scheduledForDynamicCron.setCron("* 0 * * * ?");
            //scheduledForDynamicCron.setCron("0 0 * * * ?");
        }
    }
}
