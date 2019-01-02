package com.terrastation.sha.Controller;


import com.terrastation.sha.Entity.Lumiere;
import com.terrastation.sha.Entity.Pulverisation;
import com.terrastation.sha.Entity.Pulverisationheure;
import com.terrastation.sha.Enums.ResultEnum;
import com.terrastation.sha.Exception.ParameterErrorException;
import com.terrastation.sha.Repositary.InterrupteurRepository;
import com.terrastation.sha.Repositary.PulverisationHeureRepository;
import com.terrastation.sha.Repositary.PulverisationRepository;
import com.terrastation.sha.Service.ScheduledForDynamicCron;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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


    @RequestMapping(value = "getAll", method = RequestMethod.GET)

    public List<Pulverisation> findall() {
        return pulverisationRepository.findAll();

    }
//

    @RequestMapping(value = "add", method = RequestMethod.POST)

    public Pulverisation add(@RequestBody Pulverisation pulverisation) {
        if (pulverisationRepository.findAll().isEmpty()) {


            return pulverisationRepository.save(pulverisation);

        } else {
            Pulverisation pulverisationOld = pulverisationRepository.findAll().get(0);
            pulverisationOld.setMode(pulverisation.getMode());
            pulverisationOld.setMoisDebut(pulverisation.getMoisDebut());
            pulverisationOld.setMoisFin(pulverisation.getMoisFin());
            List<Pulverisationheure>pulverisationheures= pulverisationOld.getPulverisationheure();
            if(pulverisationheures.equals(pulverisation.getPulverisationheure()))
            {for(Pulverisationheure p:pulverisationheures){
                pulverisationHeureRepository.delete(p);
                pulverisationOld.setPulverisationheure(pulverisation.getPulverisationheure());

            }}

            pulverisationOld.setTaux_hygrometrie_max(pulverisation.getTaux_hygrometrie_max());
            pulverisationOld.setTaux_hygrometrie_min(pulverisation.getTaux_hygrometrie_min());
            pulverisationRepository.save(pulverisationOld);
            return pulverisationRepository.save(pulverisationOld);
        }

    }


    @RequestMapping(value = "addHeure", method = RequestMethod.GET)

    public Pulverisation addHeure(@RequestParam("heure") int heure,@RequestParam("duree") int duree) {
        if (pulverisationRepository.findAll().isEmpty()) {
            throw new ParameterErrorException(ResultEnum.Configuration_pulverisation);
        }
        Pulverisationheure pulverisationheure=new Pulverisationheure();
        pulverisationheure.setDuree(duree);
        pulverisationheure.setHeure(heure);
        Pulverisation pulverisationOld = pulverisationRepository.findAll().get(0);
        List<Pulverisationheure> pulverisationheures = pulverisationOld.getPulverisationheure();
        pulverisationheures.add(pulverisationheure);
        return pulverisationRepository.save(pulverisationOld);


    }

    @PostMapping("/UpdateTouteLannee")
    public Pulverisation updateTouteLannee(@RequestBody Pulverisation pulverisation)
    {     if (pulverisationRepository.findAll().isEmpty()) {
             pulverisation.setMoisDebut(1);
             pulverisation.setMoisFin(12);
             return pulverisationRepository.save(pulverisation);

    } else {
        Pulverisation pulverisationOld = pulverisationRepository.findAll().get(0);
        List<Pulverisationheure>pulverisationheures= pulverisationOld.getPulverisationheure();
        if(pulverisationheures.equals(pulverisation.getPulverisationheure()))
        { for(Pulverisationheure p:pulverisationheures){
            pulverisationHeureRepository.delete(p);
            pulverisationOld.setPulverisationheure(pulverisation.getPulverisationheure());

        }}
        pulverisationOld.setMode(pulverisation.getMode());
        pulverisationOld.setMoisDebut(1);
        pulverisationOld.setMoisFin(12);
        pulverisationOld.setTaux_hygrometrie_max(pulverisation.getTaux_hygrometrie_max());
        pulverisationOld.setTaux_hygrometrie_min(pulverisation.getTaux_hygrometrie_min());
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
//    @GetMapping("test")
//    public void test() {
//        scheduledForDynamicCron.setCron("0/5 * * * * ?");
//    }
//



}