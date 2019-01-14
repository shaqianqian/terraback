package com.terrastation.sha;

import com.terrastation.sha.Controller.TerrariumController;
import com.terrastation.sha.Entity.Interrupteur;
import com.terrastation.sha.Entity.Pulverisation;
import com.terrastation.sha.Entity.PulverisationInterrupteur;
import com.terrastation.sha.Repositary.PulverisationHeureRepository;
import com.terrastation.sha.Repositary.PulverisationInterrupeurRepository;
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
    @Autowired
    private PulverisationInterrupeurRepository pulverisationInterrupeurRepository;


    @Override
    public void run(String... args) throws Exception {


//////////////////////////////////////////////////////////////////////////////////////////

        if (pulverisationRepository.findAll().isEmpty()) {
            log.info("vous avez pas encore configurez la pulverisation");

        } else {
            if(pulverisationInterrupeurRepository.findAll().size()==0){
                PulverisationInterrupteur pulverisationInterrupteur=new PulverisationInterrupteur();
                pulverisationInterrupteur.setMode("");
                pulverisationInterrupeurRepository.save(pulverisationInterrupteur);

            }
            PulverisationInterrupteur pulverisationInterrupeur = pulverisationInterrupeurRepository.findAll().get(0);
//            Pulverisation pulverisation = pulverisationRepository.findAll().get(0);

            if (pulverisationInterrupeur.getMode() == null || pulverisationInterrupeur.getMode().isEmpty()) {

                log.info("vous avez pas encore configurez la mode de pulverisation");

            } else if (pulverisationInterrupeur.getMode().equals("horaire")) {

                if (!pulverisationRepository.findByMode("horaire").isPresent()){
                    log.info("vous avez pas encore configurez les details de pulverisation en mode horaire ");

                }
                else{
                List<Pulverisation> pulverisationList=pulverisationRepository.findByMode("horaire").get();
                Calendar auj = Calendar.getInstance();
                int mois = auj.get(Calendar.MONTH) + 1;
                Pulverisation pulverisationCourant = pulverisationList.get(0);
                for (Pulverisation p : pulverisationList) {
                    if (p.getMoisDebut() <= mois && mois <= p.getMoisFin()) {
                        pulverisationCourant = p;
                        break;
                    }
                }
                if (pulverisationCourant.getPulverisationheure().size() == 0) {

                    log.info("vous avez pas encore configurez les details de pulverisation en mode horaire ");
                } else {
                    String moi = pulverisationCourant.getMoisDebut() + "-" +pulverisationCourant.getMoisFin();
                    String heures =  pulverisationCourant.getPulverisationheure().get(0).getHeure() + "";
                    Calendar c = Calendar.getInstance();
                    int heureCurrent = c.get(Calendar.HOUR_OF_DAY);

                    int dureeCorrespendant = pulverisationCourant.getPulverisationheure().get(0).getDuree();
                    if (pulverisationCourant.getPulverisationheure().size() > 1) {
                        for (int i = 1; i < pulverisationCourant.getPulverisationheure().size(); i++) {

                            heures = heures + "," + pulverisationCourant.getPulverisationheure().get(i).getHeure();
                        }
                    }
                    String cron = MessageFormat.format("0 * {0} ? {1} ?", heures, moi);
                    System.out.println(cron);
                    dynamicTaskService.startCron(pulverisationCourant, cron);
                }
            }}
        }
    }
//////////////////////////////////////////////////////////////////////////////////////////
}