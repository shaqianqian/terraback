package com.terrastation.sha.Service.Impl;

import com.terrastation.sha.Entity.Chauffage;

import com.terrastation.sha.Entity.Interrupteur;
import com.terrastation.sha.Entity.Terrarium;
import com.terrastation.sha.Enums.ResultEnum;
import com.terrastation.sha.Exception.IdNotExistException;
import com.terrastation.sha.Repositary.InterrupteurRepository;
import com.terrastation.sha.Repositary.ChauffageRepository;
import com.terrastation.sha.Repositary.TerrariumRepositary;
import com.terrastation.sha.Service.InterrupteurService;
import com.terrastation.sha.Util.ResultUtil;
import com.terrastation.sha.VO.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class InterrupteurServiceImpl implements InterrupteurService {
    @Autowired
    private InterrupteurRepository interrupteurRepository;

    @Autowired
    private TerrariumRepositary terrariumRepositary;
    @Autowired
    private ChauffageRepository chauffageRepositary;

    public Interrupteur InterrupterProgrammable() {
        Interrupteur heater = new Interrupteur();

        Optional<Interrupteur> interrupteurOptional = interrupteurRepository.findByType("chauffage");
        if (!interrupteurOptional.isPresent()) {
            Interrupteur newHeater = new Interrupteur();
            newHeater.setEtat(false);
            newHeater.setProg(true);
            newHeater.setType("chauffage");
            heater = interrupteurRepository.save(newHeater);
        }
       else { heater = interrupteurOptional.get();}

        Terrarium terrarium_current = terrariumRepositary.findCurrentParametre();
        Date currentTime = terrarium_current.getCreateTime();
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentTime);
        int month = cal.get(Calendar.MONTH) + 1;
//        log.info("月份"+month);
        int heure = cal.get(Calendar.HOUR) + 12;
//        log.info("月份 "+month+" 小时 "+heure);
        double currentTemperature = terrarium_current.getTemperature();
        Boolean etat = heater.isEtat();
        if (etat) {
            log.info("Votre chauffage est ouvert, il est programmé");
        } else {
            log.info("Votre chauffage est ferme，il est programmé");
        }
        log.info("La temperature courante est :" + currentTemperature);
        List<Chauffage> chauffages = chauffageRepositary.findAll();
        for (Chauffage c : chauffages) {

            if (c.getMoisDebut() <= month && c.getMoisFin() >= month && c.getHeureDebut() <= heure && heure <= c.getHeureFin()) {
                log.info("Le MAXIMUM de temperautre est " + c.getMax());
                log.info("Le MINIMUM de temperautre est " + c.getMin());
                if (c.getMax() < currentTemperature) {
                    if (etat) {
                        heater.setEtat(false);
                        interrupteurRepository.save(heater);
                        try {
                            log.info("START : Lancer le script du chauffage pour l'eteindre");
                            Process pr = Runtime.getRuntime().exec("python ../python/chauffage_test.py 0");

                            BufferedReader in = new BufferedReader(new
                                    InputStreamReader(pr.getInputStream()));
                            String line;
                            while ((line = in.readLine()) != null) {
                                System.out.println(line);
                            }
                            in.close();
                            pr.waitFor();
                            log.info("END : On a reussi à l'eteindre");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

//                    log.info("trop chaud, eteindre le chauffage");

                    } else {
                        log.info("Le chauffage est deja fermé, on ne change pas l'état du chauffage");
                    }

                } else if (c.getMin() > currentTemperature) {
                    if (!etat) {
                        heater.setEtat(true);
                        interrupteurRepository.save(heater);
                        log.info("Trop froid, offrir le chauffage");

                        try {
                            log.info("START : Lancer le script de chauffage pour ouvrir le chauffage");
                            Process pr = Runtime.getRuntime().exec("python ../python/chauffage_test.py 1");

                            BufferedReader in = new BufferedReader(new
                                    InputStreamReader(pr.getInputStream()));
                            String line;
                            while ((line = in.readLine()) != null) {
                                System.out.println(line);
                            }
                            in.close();
                            pr.waitFor();
                            log.info("END : on a reussi à ouvrir le chauffage");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        log.info("Le chauffage est deja ouvert, on ne change pas l'etat du chauffage");
                    }
                } else if (currentTemperature <= c.getMax() && currentTemperature >= c.getMin()) {
                    log.info("La temperature a l'air correcte manintenant, on ne change pas l'etat du chauffage");
                }


            }

//            if (c.getDateDebut().before(currentTime) && c.getDateFin().after(currentTime)) {
//                log.info("max_limite_temperautre "+c.getMax());
//                log.info("min_limite_temperautre "+c.getMin());
//                if (c.getMax() <currentTemperature) {
//                    if(etat)
//                    {heater.setEtat(false);
//                    heaterRepository.save(heater);
//                    log.info("trop chaud, eteindre le chauffage");}
//                    else{log.info("change pas letat de chauffage");}
//                } else if (c.getMin()>currentTemperature) {
//                    if(!etat)
//                    {heater.setEtat(true);
//                    heaterRepository.save(heater);
//                    log.info("trop froid, offrir le chauffage");}
//                    else{log.info("change pas letat de chauffage");}
//                }else if(currentTemperature<=c.getMax()&&currentTemperature>=c.getMin()){
//                    log.info("change pas letat de chauffage");
////                    heaterRepository.save(heater);
//                }
//
//            }

        }


        return heater;

    }

    public void changeEtatInterrupterManuel(boolean isProg) {


    }

    public Interrupteur changeControleInterrupteur(boolean isProg) {

        Optional<Interrupteur> interrupteurOptional = interrupteurRepository.findByType("chauffage");
        Interrupteur newInterrupteur = new Interrupteur();
        if (interrupteurOptional.isPresent()) {
            Interrupteur oldInterrupteur = interrupteurOptional.get();

            newInterrupteur.setEtat(oldInterrupteur.isEtat());
            newInterrupteur.setId(oldInterrupteur.getId());
            newInterrupteur.setType(oldInterrupteur.getType());
            newInterrupteur.setProg(isProg);

        }
        else{
            newInterrupteur = new Interrupteur();
            newInterrupteur.setEtat(false);
            newInterrupteur.setProg(true);
            newInterrupteur.setType("chauffage");
            interrupteurRepository.save(newInterrupteur);
        }



        return newInterrupteur;

    }

    public boolean getControleInterrupteur() {
        Optional<Interrupteur> interrupteurOptional = interrupteurRepository.findByType("chauffage");
        Interrupteur interrupteur = new Interrupteur();
        if (!interrupteurOptional.isPresent()) {
            Interrupteur newHeater = new Interrupteur();
            newHeater.setEtat(false);
            newHeater.setProg(true);
            newHeater.setType("chauffage");
            interrupteur = interrupteurRepository.save(newHeater);
        }
        else {interrupteur= interrupteurOptional.get();}

        return interrupteur.isProg();

    }

}
