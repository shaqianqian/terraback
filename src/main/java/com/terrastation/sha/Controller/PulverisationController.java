package com.terrastation.sha.Controller;


import com.terrastation.sha.Entity.Pulverisation;
import com.terrastation.sha.Entity.PulverisationInterrupteur;
import com.terrastation.sha.Entity.Pulverisationheure;
import com.terrastation.sha.Enums.ResultEnum;
import com.terrastation.sha.Exception.ParameterErrorException;
import com.terrastation.sha.Repositary.PulverisationHeureRepository;
import com.terrastation.sha.Repositary.PulverisationInterrupeurRepository;
import com.terrastation.sha.Repositary.PulverisationRepository;
import com.terrastation.sha.Service.DynamicTaskService;
import com.terrastation.sha.Service.TerrariumService;
import com.terrastation.sha.Util.ResultUtil;
import com.terrastation.sha.Util.TimeOverlappingintervals;
import com.terrastation.sha.VO.PulverisationVO;
import com.terrastation.sha.VO.ResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static com.terrastation.sha.Util.TimeOverlappingintervals.*;

//todo update all
@CrossOrigin
@RestController
@RequestMapping(value = "/terrarium/pulverisation")
public class PulverisationController {
    Logger log = LoggerFactory.getLogger(com.terrastation.sha.Controller.PulverisationController.class);


    @Autowired
    private PulverisationRepository pulverisationRepository;

    @Autowired
    private PulverisationHeureRepository pulverisationHeureRepository;


    @Autowired
    private DynamicTaskService dynamicTaskService;
    @Autowired
    private TerrariumService terrariumService;
    @Autowired
    private PulverisationInterrupeurRepository pulverisationInterrupeurRepository;

    @RequestMapping(value = "getMode", method = RequestMethod.GET)

    public ResultVO<PulverisationInterrupteur> getMode() {

        PulverisationInterrupteur pulverisationInterrupeur = pulverisationInterrupeurRepository.findAll().get(0);
        if (pulverisationInterrupeur == null) {
            PulverisationInterrupteur pulverisationInterrupteur = new PulverisationInterrupteur();
            pulverisationInterrupteur.setMode("horaire");
            pulverisationInterrupeurRepository.save(pulverisationInterrupteur);
            pulverisationInterrupeur = pulverisationInterrupeurRepository.findAll().get(0);
        }
        return ResultUtil.success(pulverisationInterrupeur);
    }


    @RequestMapping(value = "getConfigurationHoraire", method = RequestMethod.GET)

    public ResultVO<List<Pulverisation>> getConfigurationHoraire() {
        if (!pulverisationRepository.findByMode("horaire").isPresent()) {
            log.info("Vous configurez pas encore pulverisation en mode horaire ");
            return ResultUtil.success(null);
        } else {

            return ResultUtil.success(pulverisationRepository.findByMode("horaire").get());
        }


    }

    @RequestMapping(value = "getConfigurationHygrometrie", method = RequestMethod.GET)

    public ResultVO<List<Pulverisation>> getConfigurationHygrometrie() {
        if (!pulverisationRepository.findByMode("hygrometrie").isPresent()) {
            log.info("Vous configurez pas encore pulverisation en mode hygrometrie");
            return ResultUtil.success(null);
        } else {

            return ResultUtil.success(pulverisationRepository.findByMode("hygrometrie").get());
        }


    }


    @RequestMapping(value = "getAll", method = RequestMethod.GET)

    public ResultVO<List<Pulverisation>> getAll() {
        PulverisationInterrupteur pulverisationInterrupeur = pulverisationInterrupeurRepository.findAll().get(0);
        if (pulverisationInterrupeur == null) {
            PulverisationInterrupteur pulverisationInterrupteur = new PulverisationInterrupteur();
            pulverisationInterrupteur.setMode("horaire");
            pulverisationInterrupeurRepository.save(pulverisationInterrupteur);
            pulverisationInterrupeur = pulverisationInterrupeurRepository.findAll().get(0);
        }

        String mode=pulverisationInterrupeur.getMode();
       //List<PulverisationVO> pulverisationVOS=new ArrayList<PulverisationVO>();
        if(mode.equals("horaire")){
            if (!pulverisationRepository.findByMode("horaire").isPresent()) {
                log.info("Vous configurez pas encore pulverisation en mode horaire ");
                return ResultUtil.success(null);
            } else {

                return ResultUtil.success(pulverisationRepository.findByMode("horaire").get());
            }


        } else if (mode.equals("hygrometrie")) {
            if (!pulverisationRepository.findByMode("hygrometrie").isPresent()) {
                log.info("Vous configurez pas encore pulverisation en mode hygrometrie");
                return ResultUtil.success(null);
            } else {

                return ResultUtil.success(pulverisationRepository.findByMode("hygrometrie").get());
            }


        }

        else {return null;}

    }

    @RequestMapping(value = "configureModeHoraire", method = RequestMethod.POST)

    public ResultVO<List<Pulverisation>> configureModeHoraire(@RequestBody List<Pulverisation> pulverisations) {

        if (!analyeMoisPulverisationHoraire(pulverisations)) {
            log.info("il existe les chevauchements d'heure ou mois");
            throw new ParameterErrorException(ResultEnum.Existe_chevauchement);

        }
        if (!pulverisationRepository.findByMode("horaire").isPresent()) {
            for (Pulverisation pulverisation : pulverisations) {
                if (pulverisation.getMoisFin() < pulverisation.getMoisDebut()) {

                    throw new ParameterErrorException(ResultEnum.Time_Ordre);

                }
                pulverisation.setMode("horaire");
                pulverisationRepository.save(pulverisation);
            }

        } else {
            List<Pulverisation> old_configuration_pulverisation = pulverisationRepository.findByMode("horaire").get();
            for (Pulverisation pulverisation : old_configuration_pulverisation) {
                pulverisationRepository.delete(pulverisation);

            }
            for (Pulverisation pulverisation : pulverisations) {
                pulverisation.setMode("horaire");
                pulverisationRepository.save(pulverisation);

            }
        }
        ////////////////////////////////////////////
        List<Pulverisation> pulverisationsNew = pulverisationRepository.findByMode("horaire").get();
        Pulverisation pulverisationCourant = pulverisationsNew.get(0);
        Calendar c = Calendar.getInstance();
        int mois = c.get(Calendar.MONTH) + 1;
        for (Pulverisation p : pulverisationsNew) {
            if (p.getMoisDebut() <= mois && mois <= p.getMoisFin()) {
                pulverisationCourant = p;
                break;
            }

        }
        this.activeCron(pulverisationCourant);
        ////////////////////////////////////////////
        return ResultUtil.success(pulverisationsNew);


    }


    @RequestMapping(value = "configureModeHygrometrie", method = RequestMethod.POST)

    public ResultVO<List<Pulverisation>> configureModeHygrometrie(@RequestBody List<Pulverisation> pulverisations) {
        if (!analyeMoisPulverisationHygrometrie(pulverisations)) {
            log.info("il existe les chevauchements d'heure ou mois");
            throw new ParameterErrorException(ResultEnum.Existe_chevauchement);

        }
        if (!pulverisationRepository.findByMode("hygrometrie").isPresent()) {
            for (Pulverisation pulverisation : pulverisations) {
                if (pulverisation.getMoisFin() < pulverisation.getMoisDebut()) {

                    throw new ParameterErrorException(ResultEnum.Time_Ordre);

                }
                pulverisation.setMode("hygrometrie");
                pulverisationRepository.save(pulverisation);
            }

        } else {


            List<Pulverisation> old_configuration_pulverisation = pulverisationRepository.findByMode("hygrometrie").get();
            for (Pulverisation pulverisation : old_configuration_pulverisation) {
                pulverisationRepository.delete(pulverisation);

            }
            for (Pulverisation pulverisation : pulverisations) {
                pulverisation.setMode("hygrometrie");
                pulverisationRepository.save(pulverisation);

            }
        }
        List<Pulverisation> pulverisationsNew = pulverisationRepository.findByMode("hygrometrie").get();
        return ResultUtil.success(pulverisationsNew);
    }


    @RequestMapping(value = "configureModeHoraireTouteAnnee", method = RequestMethod.POST)

    public ResultVO<List<Pulverisation>> configureModeHoraireTouteAnnee(@RequestBody Pulverisation pulverisation) {
        if (!analyeMoisPulverisationHorairePulverisationHeure(pulverisation)) {
            log.info("il existe les chevauchements d'heure ou mois");
            throw new ParameterErrorException(ResultEnum.Existe_chevauchement);

        }
        if (!pulverisationRepository.findByMode("horaire").isPresent()) {
            pulverisation.setMode("horaire");
            pulverisation.setMoisDebut(1);
            pulverisation.setMoisFin(12);
            pulverisationRepository.save(pulverisation);
        } else {
            List<Pulverisation> old_configuration_pulverisation = pulverisationRepository.findByMode("horaire").get();
            for (Pulverisation old_pulverisation : old_configuration_pulverisation) {
                pulverisationRepository.delete(old_pulverisation);

            }
            pulverisation.setMode("horaire");
            pulverisation.setMoisDebut(1);
            pulverisation.setMoisFin(12);
            pulverisationRepository.save(pulverisation);


        }
        ////////////////////////////////////////////
        List<Pulverisation> pulverisationsNew = pulverisationRepository.findByMode("horaire").get();
        Pulverisation pulverisationCourant = pulverisationsNew.get(0);
        Calendar c = Calendar.getInstance();
        int mois = c.get(Calendar.MONTH) + 1;
        for (Pulverisation p : pulverisationsNew) {
            if (p.getMoisDebut() <= mois && mois <= p.getMoisFin()) {
                pulverisationCourant = p;
                break;
            }

        }
        this.activeCron(pulverisationCourant);
        ////////////////////////////////////////////
        return ResultUtil.success(pulverisationsNew);


    }


    @GetMapping("/changeMode")
    public ResultVO<PulverisationInterrupteur> changeMode(@RequestParam(value = "mode") String mode) {


        if (!mode.equals("horaire") && !mode.equals("hygrometrie")) {
            throw new ParameterErrorException(ResultEnum.PARAM_ERROR);

        }

        PulverisationInterrupteur pulverisationInterrupeur = pulverisationInterrupeurRepository.findAll().get(0);
        if (pulverisationInterrupeur.getMode().equals("horaire")) {
            //if old mode is horaire, need to close the cron
            dynamicTaskService.stopCron();
        }

        pulverisationInterrupeur.setMode(mode);
        pulverisationInterrupeurRepository.save(pulverisationInterrupeur);
        log.info("La mode de pulverisation change a " + mode);


        if (pulverisationInterrupeur.getMode().equals("horaire")) {
            if (!pulverisationRepository.findByMode("horaire").isPresent()) {
                log.info("vous configurez pas encore la pulverisation en mode horaire");

            } else {

                //////////////////////////////////////
                List<Pulverisation> pulverisationList = pulverisationRepository.findByMode("horaire").get();
                Calendar c = Calendar.getInstance();
                int mois = c.get(Calendar.MONTH) + 1;
                Pulverisation pulverisationCourant = pulverisationList.get(0);
                for (Pulverisation p : pulverisationList) {
                    if (p.getMoisDebut() <= mois && mois <= p.getMoisFin()) {
                        pulverisationCourant = p;
                        break;
                    }

                }
                this.activeCron(pulverisationCourant);
            }
            ////////////////////////////////////
        }
        return ResultUtil.success(pulverisationInterrupeur);


    }

    @RequestMapping(value = "configureModeHygrometrieTouteAnnee", method = RequestMethod.POST)

    public ResultVO<List<Pulverisation>> configureModeHygrometrieTouteAnnee(@RequestBody List<Pulverisation> pulverisations) {
        if (!analyeMoisTouteAnneePulverisationHygrometrie(pulverisations)) {
            log.info("il existe les chevauchements d'heure ou mois");
            throw new ParameterErrorException(ResultEnum.Existe_chevauchement);

        }

        if (!pulverisationRepository.findByMode("hygrometrie").isPresent()) {

            for (Pulverisation pulverisation : pulverisations) {
                pulverisation.setMoisDebut(1);
                pulverisation.setMoisFin(12);
                pulverisation.setMode("hygrometrie");
                pulverisationRepository.save(pulverisation);
            }

        } else {
            List<Pulverisation> old_configuration_pulverisation = pulverisationRepository.findByMode("hygrometrie").get();
            for (Pulverisation oldPulverisation : old_configuration_pulverisation) {
                pulverisationRepository.delete(oldPulverisation);

            }
            for (Pulverisation pulverisation : pulverisations) {
                pulverisation.setMoisDebut(1);
                pulverisation.setMoisFin(12);
                pulverisation.setMode("hygrometrie");
                pulverisationRepository.save(pulverisation);
            }
        }
        List<Pulverisation> pulverisationsNew = pulverisationRepository.findByMode("hygrometrie").get();
        return ResultUtil.success(pulverisationsNew);
    }


    public void activeCron(Pulverisation pulverisation) {
        if (pulverisationInterrupeurRepository.findAll().size() == 0) {
            PulverisationInterrupteur pulverisationInterrupteur = new PulverisationInterrupteur();
            pulverisationInterrupteur.setMode("horaire");
            pulverisationInterrupeurRepository.save(pulverisationInterrupteur);

        }
        PulverisationInterrupteur pulverisationInterrupeur = pulverisationInterrupeurRepository.findAll().get(0);

        if (pulverisationInterrupeur.getMode().equals("horaire")) {
            String moi = pulverisation.getMoisDebut() + "-" + pulverisation.getMoisFin();
            String heures = pulverisation.getPulverisationheure().get(0).getHeure() + "";
            Calendar c = Calendar.getInstance();
            if (pulverisation.getPulverisationheure().size() > 1) {
                for (int i = 1; i < pulverisation.getPulverisationheure().size(); i++) {
                    heures = heures + "," + pulverisation.getPulverisationheure().get(i).getHeure();

                }
            }
            //String cron = MessageFormat.format("0 * {0} ? {1} ?", heures, moi);
            String cron = MessageFormat.format("0 0 {0} ? {1} ?", heures, moi);
            System.out.println(cron);
            dynamicTaskService.startCron(pulverisation, cron);
        }


    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteulverisation(@PathVariable(value = "id") int pulverisationId) {
//        Optional<Pulverisation> pulverisations = pulverisationRepository.findById(pulverisationId);
//        Pulverisation pulverisations1 = null;
//        if (pulverisations.isPresent()) {
//            pulverisations1 = pulverisations.get();
//            pulverisationRepository.delete(pulverisations1);
//        }
//        return ResponseEntity.ok().build();
//    }


//    @GetMapping("/changeCron")
//    public String changeCron(@RequestParam(value = "cron") String cron) {
//        return dynamicTaskService.changeCron(cron);
//
//
//    }

//    @RequestMapping(value = "addHeureModeHoraire", method = RequestMethod.GET)
//
//    public ResultVO<Pulverisation> addHeureModeHoraire(@RequestParam("heure") int heure, @RequestParam("duree") int duree) {
//        if (pulverisationRepository.findAll().isEmpty()) {
//            throw new ParameterErrorException(ResultEnum.Configuration_pulverisation);
//        }
//
//        Pulverisation pulverisationOld = pulverisationRepository.findAll().get(0);
//        List<Pulverisationheure> pulverisationheures = pulverisationOld.getPulverisationheure();
//        boolean isContainsHeure = false;
//        Pulverisationheure pulverisationContainsHeure = new Pulverisationheure();
//        for (Pulverisationheure pulverisationheure : pulverisationheures) {
//            if (pulverisationheure.getHeure() == heure) {
//                isContainsHeure = true;
//                pulverisationContainsHeure = pulverisationheure;
//                break;
//
//            }
//        }
//        if (!isContainsHeure) {
//            Pulverisationheure pulverisationheure = new Pulverisationheure();
//            pulverisationheure.setDuree(duree);
//            pulverisationheure.setHeure(heure);
//            pulverisationheures.add(pulverisationheure);
//        } else {
//
//            pulverisationContainsHeure.setDuree(duree);
//        }
//
//
//        Pulverisation p = pulverisationRepository.save(pulverisationOld);
//        ////////////////////////////////////////////
//        this.activeCron(pulverisationOld);
//        ////////////////////////////////////////////
//        return ResultUtil.success(p);
//
//
//    }
    //    @RequestMapping(value = "deleteHeureModeHoraire", method = RequestMethod.GET)
//
//    public ResultVO<Pulverisation> deleteHeureModeHoraire(@RequestParam("id") int id) {
//        if (pulverisationRepository.findAll().isEmpty()) {
//            throw new ParameterErrorException(ResultEnum.Configuration_pulverisation);
//        }
//
//        Pulverisation pulverisationOld = pulverisationRepository.findAll().get(0);
//        List<Pulverisationheure> pulverisationheures = pulverisationOld.getPulverisationheure();
//        boolean isContainsHeure = false;
//        Pulverisationheure pulverisationContainsHeure = new Pulverisationheure();
//        for (Pulverisationheure pulverisationheure : pulverisationheures) {
//            if (pulverisationheure.getId() == id) {
//                isContainsHeure = true;
//                pulverisationContainsHeure = pulverisationheure;
//                break;
//
//            }
//        }
//        if (!isContainsHeure) {
//            throw new ParameterErrorException(ResultEnum.Heure_existe_pas);
//        } else {
//
//            pulverisationheures.remove(pulverisationContainsHeure);
//            pulverisationRepository.save(pulverisationOld);
//        }
//
//
//        ////////////////////////////////////////////
//        Pulverisation pulverisationCourant=pulverisationRepository.findAll().get(0);
//        Calendar c = Calendar.getInstance();
//        int mois= c.get(Calendar.MONTH)+1;
//        for(Pulverisation p:pulverisationRepository.findAll()){
//            if(p.getMoisDebut()<=mois&&mois<=p.getMoisFin()){
//                pulverisationCourant=p;
//                break;
//            }
//
//        }
//        this.activeCron(pulverisationCourant);
//        ////////////////////////////////////////////
//        return ResultUtil.success(pulverisationOld);
//
//
//    }

//
//    @RequestMapping(value = "deleteHeureById", method = RequestMethod.GET)
//
//    public ResultVO<Pulverisation> deleteHeureModeHoraire(@RequestParam("id") int id) {
//        if (pulverisationRepository.findAll().isEmpty()) {
//            throw new ParameterErrorException(ResultEnum.Configuration_pulverisation);
//        }
//
//        Optional<Pulverisationheure> pulverisationheureOptional = pulverisationHeureRepository.findById(id);
//        Pulverisation pulverisation = new Pulverisation();
//        if (pulverisationheureOptional.isPresent()) {
//
//            for (Pulverisation p : pulverisationRepository.findAll()) {
//                for (Pulverisationheure pulverisationheure : p.getPulverisationheure()) {
//                    if (pulverisationheure.getId() == id) {
//                        pulverisation = p;
//                        break;
//                    }
//                }
//            }
//
//        }
//        pulverisation.getPulverisationheure().remove(pulverisationheureOptional.get());
//        pulverisationRepository.save(pulverisation);
//
//        ////////////////////////////////////////////
//        Pulverisation pulverisationCourant = pulverisationRepository.findAll().get(0);
//        Calendar c = Calendar.getInstance();
//        int mois = c.get(Calendar.MONTH) + 1;
//        for (Pulverisation p : pulverisationRepository.findAll()) {
//            if (p.getMoisDebut() <= mois && mois <= p.getMoisFin()) {
//                pulverisationCourant = p;
//                break;
//            }
//
//        }
//        this.activeCron(pulverisationCourant);
//        ////////////////////////////////////////////
//        return ResultUtil.success(pulverisationCourant);
//
//
//    }


}
