package com.terrastation.sha.Controller;

import com.terrastation.sha.Entity.Interrupteur;
import com.terrastation.sha.Entity.Lumiere;
import com.terrastation.sha.Repositary.InterrupteurRepository;
import com.terrastation.sha.Repositary.LumiereRepository;
import com.terrastation.sha.Service.InterrupteurService;
import com.terrastation.sha.Util.ResultUtil;
import com.terrastation.sha.VO.ResultVO;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/terrarium/lumiere")
public class LumiereController {


    @Autowired
    private LumiereRepository lumiereRepository;
    @Autowired
    private InterrupteurService interrupteurService;
    @Autowired
    private InterrupteurRepository interrupteurRepository;



    @RequestMapping(value = "/getAll", method = RequestMethod.GET)

    public List<Lumiere> findall() {
        return lumiereRepository.findAll();

    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)

    public Lumiere add(@RequestParam("moisDebut") int moisDebut, @RequestParam("moisFin") int moisFin, @RequestParam("heureDebut") int heureDebut , @RequestParam("heureFin") int heureFin) {
        Lumiere lum=new Lumiere();
        lum.setMoisDebut(moisDebut);
        lum.setMoisFin(moisFin);
        lum.setHeureDebut(heureDebut);
        lum.setHeureFin(heureFin);
        return  lumiereRepository.save(lum);

    }



    @PutMapping("/{id}")
    public Lumiere updateLumiere(@PathVariable(value = "id") int lumiereId,
                              @Valid @RequestBody Lumiere lumiereDetails) {
        Optional<Lumiere> lumiere = lumiereRepository.findById(lumiereId);
        Lumiere lumiere1=null;
        if(lumiere.isPresent()) {
            lumiere1=lumiere.get();
            lumiere1.setMoisDebut(lumiereDetails.getMoisDebut());
            lumiere1.setMoisFin(lumiereDetails.getMoisFin());
            lumiere1.setHeureFin(lumiereDetails.getHeureFin());
            lumiere1.setHeureDebut(lumiereDetails.getHeureDebut());
        }
        Lumiere updatedLumiere = lumiereRepository.save(lumiere1);
        return updatedLumiere;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLumiere(@PathVariable(value = "id") int noteId) {
        Optional<Lumiere> lumieres = lumiereRepository.findById(noteId);
        Lumiere lumieres1=null;
        if(lumieres.isPresent()) {
            lumieres1=lumieres.get();
            lumiereRepository.delete(lumieres1);}
        return ResponseEntity.ok().build();
    }


    //get l'etat de chauffage
    @RequestMapping(value = "/getEtatLumiere", method = RequestMethod.GET)
    public ResultVO<Interrupteur> getEtatInterrupterLumiere() {
        Interrupteur interrupteur= interrupteurService.getControleInterrupteur("lumiere");
        return ResultUtil.success(interrupteur);

    }
    //change le facon de controler le interrupteur
    @RequestMapping(value = "/changeControleInterrupteur", method = RequestMethod.POST)
    public ResultVO<Interrupteur> changeControleInterrupteurChauffage( @RequestParam("isProg") boolean isProg) {

        Interrupteur newInterrupteur=interrupteurService.changeControleInterrupteur("lumiere",isProg);

        return ResultUtil.success(newInterrupteur);

    }



}