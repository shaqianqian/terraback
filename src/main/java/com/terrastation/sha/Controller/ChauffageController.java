package com.terrastation.sha.Controller;

import com.terrastation.sha.domain.Chauffage;
import com.terrastation.sha.domain.Prereglages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.terrastation.sha.repositary.*;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class ChauffageController {
    Logger log = LoggerFactory.getLogger(com.terrastation.sha.Controller.ChauffageController.class);


    @Autowired
    private ChauffageRepository chauffageRepository;


    @RequestMapping(value = "getAll", method = RequestMethod.GET)

    public List<Chauffage> findall() {
        return chauffageRepository.findAll();

    }

    @RequestMapping(value = "add", method = RequestMethod.POST)

    public Chauffage add(@RequestParam("dateDebut")Date dateDebut, @RequestParam("dateFin") Date dateFin, @RequestParam("min") double min , @RequestParam("max") double max, @RequestParam("etat")boolean etat) {
        Chauffage rep=new Chauffage();
        rep.setDateDebut(dateDebut);
        rep.setDateFin(dateFin);
        rep.setMin(min);
        rep.setMax(max);
        rep.setEtat(etat);
        return  chauffageRepository.save(rep);

    }



    @PutMapping("/prereglages/{id}")
    public Chauffage updateNote(@PathVariable(value = "id") int chauffageId,
                                  @Valid @RequestBody Chauffage chauffageDetails) {
        Optional<Chauffage> chauf = chauffageRepository.findById(chauffageId);
        Chauffage chauffage1=null;
        if(chauf.isPresent()) {
            chauffage1=chauf.get();
            chauffage1.setDateDebut(chauffageDetails.getDateDebut());
            chauffage1.setDateFin(chauffageDetails.getDateFin());
            chauffage1.setMin(chauffageDetails.getMin());
            chauffage1.setMax(chauffageDetails.getMax());
            chauffage1.setEtat(chauffageDetails.isEtat());

        }
        Chauffage chauffagePrereglages = chauffageRepository.save(chauffage1);
        return chauffagePrereglages;
    }




}