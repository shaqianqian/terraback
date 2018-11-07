package com.terrastation.sha.Controller;
import com.terrastation.sha.domain.Lumiere;
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
public class LumiereController {
    Logger log = LoggerFactory.getLogger(com.terrastation.sha.Controller.LumiereController.class);


    @Autowired
    private LumiereRepository lumiereRepository;


    @RequestMapping(value = "getAll", method = RequestMethod.GET)

    public List<Lumiere> findall() {
        return lumiereRepository.findAll();

    }


    @RequestMapping(value = "add", method = RequestMethod.POST)

    public Lumiere add(@RequestParam("dateDebut") Date dateDebut, @RequestParam("dateFin") Date dateFin, @RequestParam("heureOn") Date heureOn , @RequestParam("heureOff") Date heureOff,@RequestParam("etat") boolean etat) {
        Lumiere lum=new Lumiere();
        lum.setDateDebut(dateDebut);
        lum.setDateFin(dateFin);
        lum.setHeureOn(heureOn);
        lum.setHeureOff(heureOff);
        lum.setEtat(etat);
        return  lumiereRepository.save(lum);

    }



    @PutMapping("/lumiere/{id}")
    public Lumiere updateNote(@PathVariable(value = "id") int lumiereId,
                                  @Valid @RequestBody Lumiere lumiereDetails) {
        Optional<Lumiere> lumiere = lumiereRepository.findById(lumiereId);
        Lumiere lumiere1=null;
        if(lumiere.isPresent()) {
            lumiere1=lumiere.get();
            lumiere1.setDateDebut(lumiereDetails.getDateDebut());
            lumiere1.setDateFin(lumiereDetails.getDateFin());
            lumiere1.setEtat(lumiereDetails.isEtat());
            lumiere1.setHeureOff(lumiereDetails.getHeureOff());
            lumiere1.setHeureOn(lumiereDetails.getHeureOn());
        }
        Lumiere updatedLumiere = lumiereRepository.save(lumiere1);
        return updatedLumiere;
    }

    @DeleteMapping("/lumiere/{id}")
    public ResponseEntity<?> deleteReptile(@PathVariable(value = "id") int noteId) {
        Optional<Lumiere> lumieres = lumiereRepository.findById(noteId);
        Lumiere lumieres1=null;
        if(lumieres.isPresent()) {
            lumieres1=lumieres.get();
            lumiereRepository.delete(lumieres1);}
        return ResponseEntity.ok().build();
    }


}