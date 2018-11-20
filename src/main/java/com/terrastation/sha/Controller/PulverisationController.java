package com.terrastation.sha.Controller;


import com.terrastation.sha.Entity.Pulverisation;
import com.terrastation.sha.Repositary.PulverisationRepository;
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
@RequestMapping(value = "/pulverisation")
public class PulverisationController {
    Logger log = LoggerFactory.getLogger(com.terrastation.sha.Controller.PulverisationController.class);


    @Autowired
    private PulverisationRepository pulverisationRepository;


    @RequestMapping(value = "getAll", method = RequestMethod.GET)

    public List<Pulverisation> findall() {
        return pulverisationRepository.findAll();

    }


    @RequestMapping(value = "add", method = RequestMethod.POST)

    public Pulverisation add(@RequestParam("dateDebut") Date dateDebut, @RequestParam("dateFin") Date dateFin, @RequestParam("heureDebut") Date heureDebut , @RequestParam("heureFin") Date heureFin,@RequestParam("duree") int duree) {
        Pulverisation pulv=new Pulverisation();
        pulv.setDateDebut(dateDebut);
        pulv.setDateFin(dateFin);
        pulv.setHeureDebut(heureDebut);
        pulv.setHeureFin(heureFin);
        pulv.setDuree(duree);
        return  pulverisationRepository.save(pulv);

    }



    @PutMapping("/{id}")
    public Pulverisation updatePulverisation(@PathVariable(value = "id") int pulverisationId,
                                             @Valid @RequestBody Pulverisation pulverisationDetails) {
        Optional<Pulverisation> pulverisation = pulverisationRepository.findById(pulverisationId);
        Pulverisation pulverisation1=null;
        if(pulverisation.isPresent()) {
            pulverisation1=pulverisation.get();
            pulverisation1.setDateDebut(pulverisationDetails.getDateDebut());
            pulverisation1.setDateFin(pulverisationDetails.getDateFin());
            pulverisation1.setDuree(pulverisationDetails.getDuree());
            pulverisation1.setHeureDebut(pulverisationDetails.getHeureDebut());
            pulverisation1.setHeureFin(pulverisationDetails.getHeureFin());
        }
        Pulverisation updatedPulverisation = pulverisationRepository.save(pulverisation1);
        return updatedPulverisation;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteulverisation(@PathVariable(value = "id") int pulverisationId) {
        Optional<Pulverisation> pulverisations = pulverisationRepository.findById(pulverisationId);
        Pulverisation pulverisations1=null;
        if(pulverisations.isPresent()) {
            pulverisations1=pulverisations.get();
            pulverisationRepository.delete(pulverisations1);}
        return ResponseEntity.ok().build();
    }


}