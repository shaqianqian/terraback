package com.terrastation.sha.Controller;


import com.terrastation.sha.Entity.Pulverisation;
import com.terrastation.sha.Entity.Pulverisationheure;
import com.terrastation.sha.Enums.ResultEnum;
import com.terrastation.sha.Exception.ParameterErrorException;
import com.terrastation.sha.Repositary.PulverisationHeureRepository;
import com.terrastation.sha.Repositary.PulverisationRepository;
import com.terrastation.sha.Service.DynamicTaskService;
import com.terrastation.sha.Service.ScheduledForDynamicCron;
import com.terrastation.sha.Service.TerrariumService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.Calendar;
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
    @Autowired
    private TerrariumService terrariumService;


    @RequestMapping(value = "getAll", method = RequestMethod.GET)

    public List<Pulverisation> findall() {
        return pulverisationRepository.findAll();

    }

    @RequestMapping(value = "addModeHoraire", method = RequestMethod.POST)

    public Pulverisation addModeHoraire(@RequestBody Pulverisation pulverisation) {
        if (pulverisationRepository.findAll().isEmpty()) {
            if(pulverisation.getMoisFin()<=pulverisation.getMoisDebut()){

                throw new ParameterErrorException(ResultEnum.Time_Ordre);

            }

            Pulverisation pulverisationNew = pulverisationRepository.save(pulverisation);
            ////////////////////////////////////////////
            this.activeCron(pulverisationNew);
            ////////////////////////////////////////////
            return pulverisationNew;

        } else {
            Pulverisation pulverisationOld = pulverisationRepository.findAll().get(0);
            if(pulverisation.getMoisDebut()!=0)
            { pulverisationOld.setMoisDebut(pulverisation.getMoisDebut());}
            if(pulverisation.getMoisFin()!=0)
            { pulverisationOld.setMoisFin(pulverisation.getMoisFin());}
            if(pulverisationOld.getMoisFin()<=pulverisationOld.getMoisDebut()){

                throw new ParameterErrorException(ResultEnum.Time_Ordre);

            }
            List<Pulverisationheure> pulverisationheures = pulverisationOld.getPulverisationheure();
            if (!pulverisationheures.equals(pulverisation.getPulverisationheure())) {
                pulverisationheures.clear();
                pulverisationheures.addAll(pulverisation.getPulverisationheure());
            }
            pulverisationRepository.save(pulverisationOld);
            ////////////////////////////////////////////
            this.activeCron(pulverisationOld);
            ////////////////////////////////////////////
            return pulverisationOld;
        }

    }

    @RequestMapping(value = "addModeHygrometrie", method = RequestMethod.POST)

    public Pulverisation addModeHygrometrie(@RequestBody Pulverisation pulverisation) {
        if (pulverisationRepository.findAll().isEmpty()) {

            Pulverisation pulverisationNew = pulverisationRepository.save(pulverisation);
            return pulverisationNew;

        } else {
            Pulverisation pulverisationOld = pulverisationRepository.findAll().get(0);
            pulverisationOld.setTaux_hygrometrie_min(pulverisation.getTaux_hygrometrie_min());
            pulverisationOld.setTaux_hygrometrie_max(pulverisation.getTaux_hygrometrie_max());
            pulverisationOld.setDuree_hygrometrie(pulverisation.getDuree_hygrometrie());
            pulverisationRepository.save(pulverisationOld);
            return pulverisationOld;
        }

    }


    @RequestMapping(value = "addHeureModeHoraire", method = RequestMethod.GET)

    public Pulverisation addHeureModeHoraire(@RequestParam("heure") int heure, @RequestParam("duree") int duree) {
        if (pulverisationRepository.findAll().isEmpty()) {
            throw new ParameterErrorException(ResultEnum.Configuration_pulverisation);
        }

        Pulverisation pulverisationOld = pulverisationRepository.findAll().get(0);
        List<Pulverisationheure> pulverisationheures = pulverisationOld.getPulverisationheure();
        boolean isContainsHeure=false;
        Pulverisationheure pulverisationContainsHeure=new Pulverisationheure();
         for(Pulverisationheure pulverisationheure:pulverisationheures) {
             if (pulverisationheure.getHeure() == heure) {
                 isContainsHeure=true;
                 pulverisationContainsHeure=pulverisationheure;
                 break;

             }
         }
        if(!isContainsHeure)
        { Pulverisationheure pulverisationheure = new Pulverisationheure();
           pulverisationheure.setDuree(duree);
           pulverisationheure.setHeure(heure);
            pulverisationheures.add(pulverisationheure);}
        else{

            pulverisationContainsHeure.setDuree(duree);
        }


        ////////////////////////////////////////////
        this.activeCron(pulverisationOld);
        ////////////////////////////////////////////
        return pulverisationRepository.save(pulverisationOld);


    }
    @RequestMapping(value = "deleteHeureModeHoraire", method = RequestMethod.GET)

    public Pulverisation deleteHeureModeHoraire(@RequestParam("heure") int heure) {
        if (pulverisationRepository.findAll().isEmpty()) {
            throw new ParameterErrorException(ResultEnum.Configuration_pulverisation);
        }

        Pulverisation pulverisationOld = pulverisationRepository.findAll().get(0);
        List<Pulverisationheure> pulverisationheures = pulverisationOld.getPulverisationheure();
        boolean isContainsHeure=false;
        Pulverisationheure pulverisationContainsHeure=new Pulverisationheure();
        for(Pulverisationheure pulverisationheure:pulverisationheures) {
            if (pulverisationheure.getHeure() == heure) {
                isContainsHeure=true;
                pulverisationContainsHeure=pulverisationheure;
                break;

            }
        }
        if(!isContainsHeure)
        {    throw new ParameterErrorException(ResultEnum.Heure_existe_pas);
        }
        else{

            pulverisationheures.remove(pulverisationContainsHeure);
            pulverisationRepository.save(pulverisationOld);
        }


        ////////////////////////////////////////////
        this.activeCron(pulverisationOld);
        ////////////////////////////////////////////
        return pulverisationOld;


    }


    @PostMapping("/UpdateTouteLanneeModeHoraire")
    public Pulverisation updateTouteLannee(@RequestBody List<Pulverisationheure> pulverisationheures ) {
        if (pulverisationRepository.findAll().isEmpty()) {
            Pulverisation newPulveriasation = new Pulverisation();
            newPulveriasation.setMoisDebut(1);
            newPulveriasation.setMoisFin(12);
            newPulveriasation.setMode("horaire");
            newPulveriasation.setPulverisationheure(pulverisationheures);
            pulverisationRepository.save(newPulveriasation);
            ////////////////////////////////////////////
            this.activeCron(newPulveriasation);
            ////////////////////////////////////////////

            return newPulveriasation;

        } else {
            Pulverisation pulverisation = pulverisationRepository.findAll().get(0);
            if (!pulverisationheures.equals(pulverisation.getPulverisationheure())) {
                pulverisation.getPulverisationheure().clear();
                pulverisation.getPulverisationheure().addAll(pulverisationheures);
            }
            pulverisation.setMoisDebut(1);
            pulverisation.setMoisFin(12);
            pulverisationRepository.save(pulverisation);

            ////////////////////////////////////////////
            activeCron(pulverisation);
            ////////////////////////////////////////////

            return pulverisation;
        }


    }

    @GetMapping("/UpdateTouteLanneeModeHoraire")
    public Pulverisation updateTouteLanneeModeHoraire() {

        if (pulverisationRepository.findAll().isEmpty()) {
            throw new ParameterErrorException(ResultEnum.Configuration_pulverisation);
        } else {
            Pulverisation pulverisation = pulverisationRepository.findAll().get(0);
            pulverisation.setMoisDebut(1);
            pulverisation.setMoisFin(12);

            pulverisationRepository.save(pulverisation);

            ////////////////////////////////////////////
            activeCron(pulverisation);
            ////////////////////////////////////////////

            return pulverisation;
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


//    @GetMapping("/changeCron")
//    public String changeCron(@RequestParam(value = "cron") String cron) {
//        return dynamicTaskService.changeCron(cron);
//
//
//    }

    @GetMapping("/changeMode")
    public Pulverisation changeMode(@RequestParam(value = "mode") String mode) {
        if (!mode.equals("horaire") && !mode.equals("hygrometrie")) {
            throw new ParameterErrorException(ResultEnum.Mode_pulverisation);

        } else {
            Pulverisation pulverisation = pulverisationRepository.findAll().get(0);
            pulverisation.setMode(mode);
            pulverisationRepository.save(pulverisation);
            log.info("La mode de pulverisation change a " + mode);
            this.activeCron(pulverisation);

            return pulverisation;
        }
    }

    public void activeCron(Pulverisation pulverisation) {
        if (pulverisation.getMode().equals("horaire")) {
            String moi = pulverisation.getMoisDebut() + "-" + pulverisation.getMoisFin();
            String heures = pulverisation.getPulverisationheure().get(0).getHeure() + "";
            Calendar c = Calendar.getInstance();
            int heureCurrent = c.get(Calendar.HOUR_OF_DAY);
            log.info("当前时间" + heureCurrent);
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
            dynamicTaskService.startCron(cron);
        }


    }

}