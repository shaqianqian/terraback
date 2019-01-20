package com.terrastation.sha;

import com.terrastation.sha.Controller.TerrariumController;
import com.terrastation.sha.Entity.Interrupteur;
import com.terrastation.sha.Entity.Pulverisation;
import com.terrastation.sha.Entity.PulverisationInterrupteur;
import com.terrastation.sha.Entity.Terrarium;
import com.terrastation.sha.Repositary.InterrupteurRepository;
import com.terrastation.sha.Repositary.PulverisationInterrupeurRepository;
import com.terrastation.sha.Repositary.PulverisationRepository;
import com.terrastation.sha.Repositary.TerrariumRepositary;
import com.terrastation.sha.Service.AlarmeService;
import com.terrastation.sha.Service.DynamicTaskService;
import com.terrastation.sha.Service.InterrupteurService;
import com.terrastation.sha.Service.PulverisationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.List;

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
    @Autowired
    private DynamicTaskService dynamicTaskService;
    @Autowired
    private InterrupteurRepository interrupteurRepository;
    @Autowired
    private PulverisationInterrupeurRepository pulverisationInterrupeurRepository;

    @Autowired
    private AlarmeService alarmeService;

    static Boolean isFirstChauffage = true;
    static Boolean isFirstLumiere = true;

    @Scheduled(fixedRate = 30000)
    //30s une fois
    public void reportCurrentTime() {
        Terrarium terrarium_current = terrariumRepositary.getCurrentParameter();
        Interrupteur chauffageInterrupeur = interrupteurService.getControleInterrupteur("chauffage");
        if (chauffageInterrupeur.isProg()) {
            interrupteurService.InterrupterProgrammableChauffage("chauffage");
        } else {

            if (isFirstChauffage) {
                chauffageInterrupeur.setEtat(false);
                interrupteurRepository.save(chauffageInterrupeur);
                interrupteurService.InitInterrupterManuelleChauffage();
            }
            interrupteurService.InterrupterManuelleChauffage("chauffage");

        }
        Interrupteur lumiereInterrupeur = interrupteurService.getControleInterrupteur("lumiere");
        if (lumiereInterrupeur.isProg()) {
            interrupteurService.InterrupterProgrammableLumiere("lumiere");
        } else {
            if (isFirstLumiere) {
                lumiereInterrupeur.setEtat(false);
                interrupteurRepository.save(lumiereInterrupeur);
                interrupteurService.InitInterrupterManuelleLumiere();

            }
            interrupteurService.InterrupterManuelleLumiere("lumiere");
        }
        PulverisationInterrupteur pulverisationInterrupeur = pulverisationInterrupeurRepository.findAll().get(0);
        if (pulverisationInterrupeur.getMode().equals("hygrometrie")) {
            pulverisationService.pulverisationModeHygrometrie();
        }
        alarmeService.alarmeHygrometrie();
        alarmeService.alarmeTemperature();
        isFirstChauffage = false;
        isFirstLumiere = false;

    }

    @Scheduled(cron = "0 0 0 1 * ?")  //cron接受cron表达式，根据cron表达式确定定时规则
    public void chaqueMoisTaskServiceCron() {
        if (pulverisationRepository.findByMode("horaire").isPresent()) {
            List<Pulverisation> pulverisationList = pulverisationRepository.findByMode("horaire").get();
            Pulverisation pulverisationCourant = pulverisationList.get(0);
            Calendar c = Calendar.getInstance();
            int mois = c.get(Calendar.MONTH) + 1;
            for (Pulverisation p : pulverisationList) {
                if (p.getMoisDebut() <= mois && mois <= p.getMoisFin()) {
                    pulverisationCourant = p;
                    break;
                }
            }
            activeCron(pulverisationCourant);
        }

    }

    public void activeCron(Pulverisation pulverisation) {
        PulverisationInterrupteur pulverisationInterrupeur = pulverisationInterrupeurRepository.findAll().get(0);
        if (pulverisationInterrupeur.getMode().equals("horaire")) {
            String moi = pulverisation.getMoisDebut() + "-" + pulverisation.getMoisFin();
            String heures = pulverisation.getPulverisationheure().get(0).getHeure() + "";
            Calendar c = Calendar.getInstance();
            int heureCurrent = c.get(Calendar.HOUR_OF_DAY);
//            log.info("当前时间" + heureCurrent);
            int dureeCorrespendant = pulverisation.getPulverisationheure().get(0).getDuree();
            if (pulverisation.getPulverisationheure().size() > 1) {
                for (int i = 1; i < pulverisation.getPulverisationheure().size(); i++) {

                    heures = heures + "," + pulverisation.getPulverisationheure().get(i).getHeure();
                    if (pulverisation.getPulverisationheure().get(i).getHeure() == heureCurrent) {
                        dureeCorrespendant = pulverisation.getPulverisationheure().get(i).getDuree();
                    }

                }
            }
            String cron = MessageFormat.format("0 0 {0} ? {1} ?", heures, moi);
            //String cron = MessageFormat.format("0 * {0} ? {1} ?", heures, moi);
            System.out.println(cron);
            dynamicTaskService.startCron(pulverisation, cron);
        }


    }

}