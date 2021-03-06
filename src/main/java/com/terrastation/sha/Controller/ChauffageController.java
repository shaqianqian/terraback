package com.terrastation.sha.Controller;


import com.terrastation.sha.Entity.Chauffage;
import com.terrastation.sha.Entity.Interrupteur;
import com.terrastation.sha.Entity.Lumiere;
import com.terrastation.sha.Enums.ResultEnum;
import com.terrastation.sha.Exception.IdNotExistException;
import com.terrastation.sha.Exception.ParameterErrorException;
import com.terrastation.sha.Exception.TerraiumException;
import com.terrastation.sha.Repositary.ChauffageRepository;
import com.terrastation.sha.Repositary.InterrupteurRepository;
import com.terrastation.sha.Service.InterrupteurService;
import com.terrastation.sha.Util.ResultUtil;
import com.terrastation.sha.Util.TimeOverlappingintervals;
import com.terrastation.sha.VO.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping(value = "/terrarium/chauffage")
public class ChauffageController {

    @Autowired
    private ChauffageRepository chauffageRepository;

    @Autowired
    private InterrupteurService interrupteurService;
    @Autowired
    private InterrupteurRepository interrupteurRepository;


    @RequestMapping(value = "/getAll", method = RequestMethod.GET)

    public ResultVO findall() {
        return ResultUtil.success(chauffageRepository.findAll());

    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)

    public ResultVO add(@RequestBody Chauffage chauffage) {
        Chauffage newChauffage = new Chauffage();
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date timeDebut = null;
//        Date timeFin = null;
//        try {
//            timeDebut = format.parse(dateDebut);
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        try {
//            timeFin = format.parse(dateFin);
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        if (timeDebut.after(timeFin) || timeDebut.equals(timeFin)) {
//
//            throw new IdNotExistException(ResultEnum.Time_Ordre);
//        } else {
//            rep.setDateDebut(timeDebut);
//            rep.setDateFin(timeFin);

        if (chauffage.getMoisDebut() >= chauffage.getMoisFin() || chauffage.getHeureDebut() >= chauffage.getHeureFin() || chauffage.getMin() >= chauffage.getMax()) {
            throw new ParameterErrorException(ResultEnum.Time_Ordre);
        } else {
            newChauffage = chauffageRepository.save(chauffage);
        }


        return ResultUtil.success(newChauffage);

    }


    @DeleteMapping("/delete/{id}")
    public ResultVO<String> deleteChauffage(@PathVariable(value = "id") int chauffageId) {
        Optional<Chauffage> chauffage = chauffageRepository.findById(chauffageId);
        Chauffage chauffageNew = null;


        if (chauffage.isPresent()) {
            chauffageNew = chauffage.get();
            chauffageRepository.delete(chauffageNew);
        }
        chauffage.orElseThrow(() -> new IdNotExistException(ResultEnum.ID_NOT_EXIST));
        return ResultUtil.success("vous avez reussi de supprimer : " + chauffageId);
    }


    @GetMapping("/DeleteAll")
    public ResultVO<String> deleteAll() {
        List<Chauffage> oldChauffageList = chauffageRepository.findAll();
        for (Chauffage oldChauffage : oldChauffageList) {
            chauffageRepository.delete(oldChauffage);

        }

        return ResultUtil.success("vous avez supprime toute la configuration de chauffage");
    }


    @PostMapping("/UpdateAll")
    public ResultVO<List<Chauffage>> updateAll(@RequestBody List<Chauffage> chauffages) {
        if (!TimeOverlappingintervals.analyeMoisChauffage(chauffages)) {

                log.info("il existe les chevauchements d'heure ou mois");
                throw new ParameterErrorException(ResultEnum.Existe_chevauchement);

        }
        List<Chauffage> oldChauffageList = chauffageRepository.findAll();
        for (Chauffage oldChauffage : oldChauffageList) {
            chauffageRepository.delete(oldChauffage);

        }

        for (int i = 0; i < chauffages.size(); i++) {
        if (chauffages.get(i).getHeureDebut() >chauffages.get(i).getHeureFin() || chauffages.get(i).getMoisDebut() >chauffages.get(i).getMoisFin() || chauffages.get(i).getMin() >= chauffages.get(i).getMax()) {
            throw new ParameterErrorException(ResultEnum.Time_Ordre);
        }
//            chauffages.get(i).setId(i+1);
            chauffages.get(i).setId(0);
        chauffageRepository.save(chauffages.get(i));
    }


        return ResultUtil.success(chauffageRepository.findAll());
}

    @PostMapping("/UpdateTouteLannee")
    public ResultVO<List<Chauffage>> updateTouteLannee(@RequestBody List<Chauffage> chauffages) {
        if (!TimeOverlappingintervals.analyeMoisTouteAnneeChauffage(chauffages)) {

            log.info("il existe les chevauchements d'heure");
            throw new ParameterErrorException(ResultEnum.Existe_chevauchement);

        }
        List<Chauffage> oldChauffageList = chauffageRepository.findAll();
        for (Chauffage oldChauffage : oldChauffageList) {
//            if (oldChauffage.getMoisDebut() != 1 || oldChauffage.getMoisFin() != 12) {
                chauffageRepository.delete(oldChauffage);
//            }
        }
        for (Chauffage c : chauffages) {
            Chauffage chauffage = new Chauffage();
            if (c.getHeureDebut() > c.getHeureFin() || c.getMin() >= c.getMax()) {
                throw new ParameterErrorException(ResultEnum.Time_Ordre);
            }

            chauffage.setHeureDebut(c.getHeureDebut());
            chauffage.setHeureFin(c.getHeureFin());
            chauffage.setMoisDebut(1);
            chauffage.setMoisFin(12);
            chauffage.setMax(c.getMax());
            chauffage.setMin(c.getMin());
            chauffageRepository.save(chauffage);
        }


        return ResultUtil.success(chauffageRepository.findAll());
    }


    @PutMapping("/update/{id}")
    public ResultVO<Chauffage> update(@PathVariable(value = "id") int chauffageId,
//                                          @RequestParam(value = "dateDebut", required = true, defaultValue = "1900-01-01 00:00:00") String dateDebut,
//                                          @RequestParam(value = "dateFin", required = true, defaultValue = "1900-01-01 00:00:00") String dateFin,
                                      @RequestParam(value = "max", required = true, defaultValue = "0") double max,
                                      @RequestParam(value = "min", required = true, defaultValue = "0") double min,
                                      @RequestParam(value = "moisDebut", required = true, defaultValue = "0") int moisDebut,
                                      @RequestParam(value = "moisFin", required = true, defaultValue = "12") int moisFin,
                                      @RequestParam(value = "heureDebut", required = true, defaultValue = "0") int heureDebut,
                                      @RequestParam(value = "heureFin", required = true, defaultValue = "24") int heureFin) {

        Optional<Chauffage> chauffageOptional = chauffageRepository.findById(chauffageId);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Chauffage chauffageNew = null;
        if (chauffageOptional.isPresent()) {
            chauffageNew = chauffageOptional.get();
//            if (!dateDebut.equals("1900-01-01 00:00:00")) {
//                try {
//                    chauffageNew.setDateDebut(format.parse(dateDebut));
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//
//            }
//            if (!dateFin.equals("1900-01-01 00:00:00")) {
//
//                try {
//                    chauffageNew.setDateFin(format.parse(dateFin));
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//
//            }

            if (moisDebut != 0) {
                chauffageNew.setMoisDebut(moisDebut);
            }

            if (moisFin != 0) {
                chauffageNew.setMoisFin(moisFin);
            }

            if (heureDebut != 0) {
                chauffageNew.setHeureDebut(heureDebut);
            }
            if (heureFin != 0) {
                chauffageNew.setHeureFin(heureFin);
            }
            if (chauffageNew.getMoisDebut() >= chauffageNew.getMoisFin()) {
                throw new ParameterErrorException(ResultEnum.Time_Ordre);
            }
            if (chauffageNew.getHeureDebut() >= chauffageNew.getHeureFin()) {
                throw new ParameterErrorException(ResultEnum.Time_Ordre);
            }


            if (max != 0) {
                chauffageNew.setMax(max);
            }

            if (min != 0) {
                chauffageNew.setMin(min);
            }


        }
        Chauffage chauffage1 = chauffageRepository.save(chauffageNew);
        return ResultUtil.success(chauffage1);
    }

    //get l'etat de chauffage
    @RequestMapping(value = "/getEtat", method = RequestMethod.GET)


    public ResultVO<Interrupteur> getEtatChauffage() {
        Interrupteur interrupteur = interrupteurService.getControleInterrupteur("chauffage");
        return ResultUtil.success(interrupteur);

    }

    //change le facon de controler le interrupteur
    @RequestMapping(value = "/changeControleInterrupteur", method = RequestMethod.GET)
    public ResultVO<Interrupteur> changeControleInterrupteurChauffage() {
        Interrupteur interrupteur = interrupteurService.getControleInterrupteur("chauffage");
        boolean isProg=interrupteur.isProg();
        Interrupteur newInterrupteur = interrupteurService.changeControleInterrupteur("chauffage", !isProg);
        return ResultUtil.success(newInterrupteur);

    }


    //change l'etat de chauffage quand il est controle manuellement
    @RequestMapping(value = "/changeEtatInterrupteurManuellement", method = RequestMethod.GET)
    public ResultVO<Interrupteur> changeEtatInterrupteurManuellement() {
        Interrupteur interrupteur = interrupteurService.getControleInterrupteur("chauffage");
        boolean etat=interrupteur.isEtat();
        Interrupteur newInterrupteur = interrupteurService.ChangeInterrupterManuelleChauffage(!etat);

        return ResultUtil.success(newInterrupteur);

    }


}