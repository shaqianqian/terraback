package com.terrastation.sha.Controller;

import com.terrastation.sha.Entity.Chauffage;
import com.terrastation.sha.Entity.Interrupteur;
import com.terrastation.sha.Entity.Lumiere;
import com.terrastation.sha.Enums.ResultEnum;
import com.terrastation.sha.Exception.TerraiumException;
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

    public ResultVO<List<Lumiere>> findall() {
        return  ResultUtil.success(lumiereRepository.findAll());

    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)

    public ResultVO<Lumiere> add(@RequestBody Lumiere lumiere) {
//        Lumiere lum=new Lumiere();
//        lum.setMoisDebut(moisDebut);
//        lum.setMoisFin(moisFin);
//        lum.setHeureDebut(heureDebut);
//        lum.setHeureFin(heureFin);
        return  ResultUtil.success(lumiereRepository.save(lumiere));

    }



    @PutMapping("/{id}")
    public  ResultVO<Lumiere> updateLumiere(@PathVariable(value = "id") int lumiereId,
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
        return ResultUtil.success(updatedLumiere);
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


    @PostMapping("/UpdateAll")
    public ResultVO<List<Lumiere>> updateAll(@RequestBody List<Lumiere> lumieres)
    {
        List<Lumiere> oldChauffageList=lumiereRepository.findAll();
        for(Lumiere oldLumiere: oldChauffageList)
        {
            lumiereRepository.delete(oldLumiere);

        }
        for(int i=0;i<lumieres.size();i++){
            lumiereRepository.save(lumieres.get(i));
        }


        return ResultUtil.success(lumiereRepository.findAll());
    }





    //get l'etat de chauffage
    @RequestMapping(value = "/getEtatLumiere", method = RequestMethod.GET)
    public ResultVO<Interrupteur> getEtatInterrupterLumiere() {
        Interrupteur interrupteur= interrupteurService.getControleInterrupteur("lumiere");
        return ResultUtil.success(interrupteur);

    }
    //change le facon de controler le interrupteur
    @RequestMapping(value = "/changeControleInterrupteur", method = RequestMethod.POST)
    public ResultVO<Interrupteur> changeControleInterrupteurLumiere( @RequestParam("isProg") boolean isProg) {

        Interrupteur newInterrupteur=interrupteurService.changeControleInterrupteur("lumiere",isProg);

        return ResultUtil.success(newInterrupteur);

    }

    //change l'etat de la lumiere quand il est controle manuellement
    @RequestMapping(value = "/changeEtatInterrupteurManuellement", method = RequestMethod.POST)
    public ResultVO<Interrupteur> changeEtatInterrupteurManuellement( @RequestParam("etat") boolean etat) {
        Interrupteur newInterrupteur=interrupteurService.ChangeInterrupterManuelleLumiere(etat);
        return ResultUtil.success(newInterrupteur);

    }



}