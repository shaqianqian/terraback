package com.terrastation.sha.Controller;

import com.terrastation.sha.Entity.Chauffage;
import com.terrastation.sha.Entity.Interrupteur;
import com.terrastation.sha.Entity.Lumiere;
import com.terrastation.sha.Enums.ResultEnum;
import com.terrastation.sha.Exception.ParameterErrorException;
import com.terrastation.sha.Exception.TerraiumException;
import com.terrastation.sha.Repositary.InterrupteurRepository;
import com.terrastation.sha.Repositary.LumiereRepository;
import com.terrastation.sha.Service.InterrupteurService;
import com.terrastation.sha.Util.ResultUtil;
import com.terrastation.sha.Util.TimeOverlappingintervals;
import com.terrastation.sha.VO.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@Slf4j
@CrossOrigin
@RequestMapping(value = "/terrarium/lumiere")
public class LumiereController {


    @Autowired
    private LumiereRepository lumiereRepository;
    @Autowired
    private InterrupteurService interrupteurService;
    @Autowired
    private InterrupteurRepository interrupteurRepository;


    @RequestMapping(value = "/getAll", method = RequestMethod.GET)

    public ResultVO<List<Lumiere>> findall() {
        return ResultUtil.success(lumiereRepository.findAll());

    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)

    public ResultVO<Lumiere> add(@RequestBody Lumiere lumiere) {
        if (lumiere.getHeureDebut() >= lumiere.getHeureFin() || lumiere.getMoisDebut() >=lumiere.getMoisFin()) {
            throw new ParameterErrorException(ResultEnum.Time_Ordre);
        }
        return ResultUtil.success(lumiereRepository.save(lumiere));

    }


    @PutMapping("/{id}")
    public ResultVO<Lumiere> updateLumiere(@PathVariable(value = "id") int lumiereId,
                                           @Valid @RequestBody Lumiere lumiereDetails) {
        Optional<Lumiere> lumiere = lumiereRepository.findById(lumiereId);
        Lumiere lumiere1 = null;
        if (lumiere.isPresent()) {
            lumiere1 = lumiere.get();
            lumiere1.setMoisDebut(lumiereDetails.getMoisDebut());
            lumiere1.setMoisFin(lumiereDetails.getMoisFin());
            lumiere1.setHeureFin(lumiereDetails.getHeureFin());
            lumiere1.setHeureDebut(lumiereDetails.getHeureDebut());
        }
        Lumiere updatedLumiere = lumiereRepository.save(lumiere1);
        return ResultUtil.success(updatedLumiere);
    }


    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteLumiere(@PathVariable(value = "id") int noteId) {
        Optional<Lumiere> lumieres = lumiereRepository.findById(noteId);
        Lumiere lumieres1 = null;
        if (lumieres.isPresent()) {
            lumieres1 = lumieres.get();
            lumiereRepository.delete(lumieres1);
        }
        return ResponseEntity.ok().build();
    }


    @PostMapping("/UpdateAll")
    public ResultVO<List<Lumiere>> updateAll(@RequestBody List<Lumiere> lumieres) {
        if (!TimeOverlappingintervals.analyeMoisLumiere(lumieres)) {
            log.info("il existe les chevauchements d'heure ou mois");
            throw new ParameterErrorException(ResultEnum.Existe_chevauchement);

        }
        List<Lumiere> oldChauffageList = lumiereRepository.findAll();
        for (Lumiere oldLumiere : oldChauffageList) {
            lumiereRepository.delete(oldLumiere);
        }
        for (int i = 0; i < lumieres.size(); i++) {
            if (lumieres.get(0).getHeureDebut() >=lumieres.get(0).getHeureFin() ||lumieres.get(0).getMoisDebut() >= lumieres.get(0).getMoisFin()) {
                throw new ParameterErrorException(ResultEnum.Time_Ordre);
            }
             lumieres.get(i).setId(0);
            lumiereRepository.save(lumieres.get(i));
        }


        return ResultUtil.success(lumiereRepository.findAll());
    }

    @GetMapping("/DeleteAll")
    public ResultVO<String> deleteAll() {
        List<Lumiere> oldChauffageList = lumiereRepository.findAll();
        for (Lumiere oldLumiere : oldChauffageList) {
            lumiereRepository.delete(oldLumiere);

        }

        return ResultUtil.success("Vous avez supprime toute la configuration de lumiere");
    }

    @PostMapping("/UpdateTouteLannee")
    public ResultVO<List<Lumiere>> updateTouteLannee(@RequestBody List<Lumiere> lumieres) {

        if (!TimeOverlappingintervals.analyeMoisTouteAnneeLumiere(lumieres)) {
            log.info("il existe les chevauchements d'heure");
            throw new ParameterErrorException(ResultEnum.Existe_chevauchement);

        }
        List<Lumiere> oldChauffageList = lumiereRepository.findAll();
        for (Lumiere oldLumiere : oldChauffageList) {
//            if(oldLumiere.getMoisDebut()!=1||oldLumiere.getMoisFin()!=12)
            //{
                lumiereRepository.delete(oldLumiere);
            //}
        }
        for (Lumiere l : lumieres) {
            Lumiere lumiere = new Lumiere();
            if (l.getHeureDebut() >= l.getHeureFin()) {
                throw new ParameterErrorException(ResultEnum.Time_Ordre);
            }

            lumiere.setHeureDebut(l.getHeureDebut());
            lumiere.setHeureFin(l.getHeureFin());
            lumiere.setMoisDebut(1);
            lumiere.setMoisFin(12);
            lumiereRepository.save(lumiere);}
        return ResultUtil.success(lumiereRepository.findAll());
    }

    //get l'etat de chauffage
    @RequestMapping(value = "/getEtatLumiere", method = RequestMethod.GET)
    public ResultVO<Interrupteur> getEtatInterrupterLumiere() {
        Interrupteur interrupteur = interrupteurService.getControleInterrupteur("lumiere");
        return ResultUtil.success(interrupteur);

    }

    //change le facon de controler le interrupteur
    @RequestMapping(value = "/changeControleInterrupteur", method = RequestMethod.GET)
    public ResultVO<Interrupteur> changeControleInterrupteurLumiere(@RequestParam("isProg") boolean isProg) {

        Interrupteur newInterrupteur = interrupteurService.changeControleInterrupteur("lumiere", isProg);

        return ResultUtil.success(newInterrupteur);

    }

    //change l'etat de la lumiere quand il est controle manuellement
    @RequestMapping(value = "/changeEtatInterrupteurManuellement", method = RequestMethod.GET)
    public ResultVO<Interrupteur> changeEtatInterrupteurManuellement() {
        Interrupteur interrupteur = interrupteurService.getControleInterrupteur("lumiere");
        boolean etat=interrupteur.isEtat();
        Interrupteur newInterrupteur = interrupteurService.ChangeInterrupterManuelleLumiere(!etat);
        return ResultUtil.success(newInterrupteur);

    }


}