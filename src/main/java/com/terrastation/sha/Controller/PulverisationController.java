package com.terrastation.sha.Controller;


import com.terrastation.sha.Entity.Lumiere;
import com.terrastation.sha.Entity.Pulverisation;
import com.terrastation.sha.Entity.Pulverisationheure;
import com.terrastation.sha.Enums.ResultEnum;
import com.terrastation.sha.Exception.ParameterErrorException;
import com.terrastation.sha.Repositary.InterrupteurRepository;
import com.terrastation.sha.Repositary.PulverisationHeureRepository;
import com.terrastation.sha.Repositary.PulverisationRepository;
import com.terrastation.sha.Service.DynamicTaskService;
import com.terrastation.sha.Service.ScheduledForDynamicCron;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/terrarium/pulverisation")
public class PulverisationController {
    Logger log = LoggerFactory.getLogger(com.terrastation.sha.Controller.PulverisationController.class);


    @Autowired
    private PulverisationRepository pulverisationRepository;
    @Autowired
    private PulverisationHeureRepository pulverisationHeureRepository;

    @Autowired
    private ScheduledForDynamicCron scheduledForDynamicCron;

    @Autowired
    private DynamicTaskService dynamicTaskService;


    @RequestMapping(value = "getAll", method = RequestMethod.GET)

    public List<Pulverisation> findall() {
        return pulverisationRepository.findAll();

    }
//

    @RequestMapping(value = "add", method = RequestMethod.POST)

    public Pulverisation add(@RequestBody Pulverisation pulverisation) {
        if (pulverisationRepository.findAll().isEmpty()) {


            Pulverisation pulverisationNew = pulverisationRepository.save(pulverisation);
////////////////////////////////////////////

            if (pulverisationNew.getMode().equals("temps")) {
                String moi = pulverisationNew.getMoisDebut() + "-" + pulverisationNew.getMoisFin();
                String heures = pulverisationNew.getPulverisationheure().get(0).getHeure() + "";
                int heureCurrent = Calendar.HOUR_OF_DAY + 1;
                int dureeCorrespendant = pulverisationNew.getPulverisationheure().get(0).getDuree();
                if (pulverisationNew.getPulverisationheure().size() > 1) {
                    for (int i = 1; i < pulverisationNew.getPulverisationheure().size(); i++) {

                        heures = heures + "," + pulverisationNew.getPulverisationheure().get(i).getHeure();
                        if (pulverisationNew.getPulverisationheure().get(i).getHeure() == heureCurrent) {

                            dureeCorrespendant = pulverisationNew.getPulverisationheure().get(i).getDuree();
                        }

                    }
                }
                String cron = MessageFormat.format("0 * {0} ? {1} ?", heures, moi);
                System.out.println(cron);

                dynamicTaskService.startCron(cron, dureeCorrespendant);
////////////////////////////////////////////
            }


            return pulverisationNew;

        } else {
            Pulverisation pulverisationOld = pulverisationRepository.findAll().get(0);
            pulverisationOld.setMode(pulverisation.getMode());
            pulverisationOld.setMoisDebut(pulverisation.getMoisDebut());
            pulverisationOld.setMoisFin(pulverisation.getMoisFin());
            List<Pulverisationheure> pulverisationheures = pulverisationOld.getPulverisationheure();
            pulverisationheures.clear();
            pulverisationheures.addAll(pulverisation.getPulverisationheure());
            pulverisationOld.setTaux_hygrometrie_max(pulverisation.getTaux_hygrometrie_max());
            pulverisationOld.setTaux_hygrometrie_min(pulverisation.getTaux_hygrometrie_min());
            pulverisationRepository.save(pulverisationOld);
////////////////////////////////////////////
            if (pulverisationOld.getMode().equals("temps")) {
                String moi = pulverisationOld.getMoisDebut() + "-" + pulverisationOld.getMoisFin();
                String heures = pulverisationOld.getPulverisationheure().get(0).getHeure() + "";
                int heureCurrent = Calendar.HOUR_OF_DAY + 1;
                int dureeCorrespendant = pulverisationOld.getPulverisationheure().get(0).getDuree();
                if (pulverisationOld.getPulverisationheure().size() > 1) {
                    for (int i = 1; i < pulverisationOld.getPulverisationheure().size(); i++) {

                        heures = heures + "," + pulverisationOld.getPulverisationheure().get(i).getHeure();
                        if (pulverisationOld.getPulverisationheure().get(i).getHeure() == heureCurrent) {

                            dureeCorrespendant = pulverisationOld.getPulverisationheure().get(i).getDuree();
                        }

                    }
                }
                String cron = MessageFormat.format("0 * {0} ? {1} ?", heures, moi);
                System.out.println(cron);
                dynamicTaskService.startCron(cron, dureeCorrespendant);
            }

////////////////////////////////////////////

            return pulverisationRepository.save(pulverisationOld);
        }

    }


    @RequestMapping(value = "addHeure", method = RequestMethod.GET)

    public Pulverisation addHeure(@RequestParam("heure") int heure, @RequestParam("duree") int duree) {
        if (pulverisationRepository.findAll().isEmpty()) {
            throw new ParameterErrorException(ResultEnum.Configuration_pulverisation);
        }
        Pulverisationheure pulverisationheure = new Pulverisationheure();
        pulverisationheure.setDuree(duree);
        pulverisationheure.setHeure(heure);
        Pulverisation pulverisationOld = pulverisationRepository.findAll().get(0);
        List<Pulverisationheure> pulverisationheures = pulverisationOld.getPulverisationheure();
        pulverisationheures.add(pulverisationheure);


        ////////////////////////////////////////////
        if ( pulverisationOld.getMode().equals("temps")) {
            String moi =  pulverisationOld.getMoisDebut() + "-" +  pulverisationOld.getMoisFin();
            String heures = pulverisationOld.getPulverisationheure().get(0).getHeure() + "";
            int heureCurrent = Calendar.HOUR_OF_DAY + 1;
            int dureeCorrespendant =  pulverisationOld.getPulverisationheure().get(0).getDuree();
            if (pulverisationOld.getPulverisationheure().size() > 1) {
                for (int i = 1; i <  pulverisationOld.getPulverisationheure().size(); i++) {

                    heures = heures + "," + pulverisationOld.getPulverisationheure().get(i).getHeure();
                    if ( pulverisationOld.getPulverisationheure().get(i).getHeure() == heureCurrent) {

                        dureeCorrespendant = pulverisationOld.getPulverisationheure().get(i).getDuree();
                    }

                }
            }
            String cron = MessageFormat.format("0 * {0} ? {1} ?", heures, moi);
            System.out.println(cron);
            dynamicTaskService.startCron(cron, dureeCorrespendant);
        }

////////////////////////////////////////////

        return pulverisationRepository.save(pulverisationOld);


    }

    @PostMapping("/UpdateTouteLannee")
    public Pulverisation updateTouteLannee(@RequestBody Pulverisation pulverisation) {
        if (pulverisationRepository.findAll().isEmpty()) {
            pulverisation.setMoisDebut(1);
            pulverisation.setMoisFin(12);

            ////////////////////////////////////////////
            if ( pulverisation.getMode().equals("temps")) {
                String moi =  pulverisation.getMoisDebut() + "-" +  pulverisation.getMoisFin();
                String heures =  pulverisation.getPulverisationheure().get(0).getHeure() + "";
                int heureCurrent = Calendar.HOUR_OF_DAY + 1;
                int dureeCorrespendant =  pulverisation.getPulverisationheure().get(0).getDuree();
                if ( pulverisation.getPulverisationheure().size() > 1) {
                    for (int i = 1; i <  pulverisation.getPulverisationheure().size(); i++) {

                        heures = heures + "," + pulverisation.getPulverisationheure().get(i).getHeure();
                        if ( pulverisation.getPulverisationheure().get(i).getHeure() == heureCurrent) {

                            dureeCorrespendant = pulverisation.getPulverisationheure().get(i).getDuree();
                        }

                    }
                }
                String cron = MessageFormat.format("0 * {0} ? {1} ?", heures, moi);
                System.out.println(cron);
                dynamicTaskService.startCron(cron, dureeCorrespendant);
            }

////////////////////////////////////////////

            return pulverisationRepository.save(pulverisation);

        } else {
            Pulverisation pulverisationOld = pulverisationRepository.findAll().get(0);
            List<Pulverisationheure> pulverisationheures = pulverisationOld.getPulverisationheure();
            pulverisationheures.clear();
            pulverisationheures.addAll(pulverisation.getPulverisationheure());
            pulverisationOld.setMode(pulverisation.getMode());
            pulverisationOld.setMoisDebut(1);
            pulverisationOld.setMoisFin(12);
            pulverisationOld.setTaux_hygrometrie_max(pulverisation.getTaux_hygrometrie_max());
            pulverisationOld.setTaux_hygrometrie_min(pulverisation.getTaux_hygrometrie_min());



            ////////////////////////////////////////////
            if ( pulverisation.getMode().equals("temps")) {
                String moi =  pulverisation.getMoisDebut() + "-" +  pulverisation.getMoisFin();
                String heures =  pulverisation.getPulverisationheure().get(0).getHeure() + "";
                int heureCurrent = Calendar.HOUR_OF_DAY + 1;
                int dureeCorrespendant =  pulverisation.getPulverisationheure().get(0).getDuree();
                if ( pulverisation.getPulverisationheure().size() > 1) {
                    for (int i = 1; i <  pulverisation.getPulverisationheure().size(); i++) {

                        heures = heures + "," + pulverisation.getPulverisationheure().get(i).getHeure();
                        if ( pulverisation.getPulverisationheure().get(i).getHeure() == heureCurrent) {

                            dureeCorrespendant = pulverisation.getPulverisationheure().get(i).getDuree();
                        }

                    }
                }
                String cron = MessageFormat.format("0 * {0} ? {1} ?", heures, moi);
                System.out.println(cron);
                dynamicTaskService.startCron(cron, dureeCorrespendant);
            }

////////////////////////////////////////////

            return pulverisationRepository.save(pulverisationOld);
        }


    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteulverisation(@PathVariable(value = "id") int pulverisationId) {
        Optional<Pulverisation> pulverisations = pulverisationRepository.findById(pulverisationId);
        Pulverisation pulverisations1 = null;
        if (pulverisations.isPresent()) {
            pulverisations1 = pulverisations.get();
            pulverisationRepository.delete(pulverisations1);
        }
        return ResponseEntity.ok().build();
    }


    @GetMapping("/changeCron")
    public String changeCron(@RequestParam(value = "cron") String cron) {
        return dynamicTaskService.changeCron(cron);


    }


}