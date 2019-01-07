package com.terrastation.sha;

import com.terrastation.sha.Controller.TerrariumController;
import com.terrastation.sha.Entity.Pulverisation;
import com.terrastation.sha.Entity.Terrarium;
import com.terrastation.sha.Repositary.PulverisationHeureRepository;
import com.terrastation.sha.Repositary.PulverisationRepository;
import com.terrastation.sha.Repositary.TerrariumRepositary;
import com.terrastation.sha.Service.InterrupteurService;
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

            Pulverisation pulverisation = pulverisationRepository.findAll().get(0);
            if (pulverisation.getMode() == null || pulverisation.getMode().isEmpty()) {

                log.info("vous avez pas encore configurez la mode de pulverisation");

            } else if (pulverisation.getMode().equals("hygrometrie")) {
                log.info("vous controlez le pulverisation en mode hygrometrie ,Humidite courant est " + terrarium_current.getHumidite());
                if(pulverisation.getDuree_hygrometrie()==0){
                    log.info("vous avez pas encore configurez la mode hygrometrie de pulverisation");

                }
                else if (terrarium_current.getHumidite() < pulverisation.getTaux_hygrometrie_min()) {
                    try {
                        log.info("Votre terrarium n'est pas assez humide, lancer la pulverisation, la duree est "+pulverisation.getDuree_hygrometrie());
                        log.info("START : Lancer le script du pulverisation");
                        Process pr = Runtime.getRuntime().exec("python ../python/pulverisation_test.py " + pulverisation.getDuree_hygrometrie());

                        BufferedReader in = new BufferedReader(new
                                InputStreamReader(pr.getInputStream()));
                        String line;
                        while ((line = in.readLine()) != null) {
                            System.out.println(line);
                        }
                        in.close();
                        pr.waitFor();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }


            }

        }

    }


}