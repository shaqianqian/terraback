package com.terrastation.sha.Service.Impl;

import com.terrastation.sha.Entity.Chauffage;

import com.terrastation.sha.Entity.Interrupteur;
import com.terrastation.sha.Entity.Lumiere;
import com.terrastation.sha.Entity.Terrarium;
import com.terrastation.sha.Enums.ResultEnum;
import com.terrastation.sha.Exception.ParameterErrorException;
import com.terrastation.sha.Repositary.InterrupteurRepository;
import com.terrastation.sha.Repositary.ChauffageRepository;
import com.terrastation.sha.Repositary.LumiereRepository;
import com.terrastation.sha.Service.InterrupteurService;
import com.terrastation.sha.Service.TerrariumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private TerrariumService terrariumService;
    @Autowired
    private ChauffageRepository chauffageRepositary;
    @Autowired
    private LumiereRepository lumiereRepositary;

    ///////////////////////////////////////General////////////////////////////////////////////////////////////////

    public Interrupteur getControleInterrupteur(String type) {
        Interrupteur interrupteur = new Interrupteur();
        Optional<Interrupteur> interrupteurOptional = interrupteurRepository.findByType(type);
        if (!interrupteurOptional.isPresent()) {
            Interrupteur newInterrupteur = new Interrupteur();
            newInterrupteur.setEtat(false);
            newInterrupteur.setProg(false);
            newInterrupteur.setType(type);
            interrupteur = interrupteurRepository.save(newInterrupteur);
        } else {
            interrupteur = interrupteurOptional.get();
        }
        return interrupteur;

    }

    public Interrupteur changeControleInterrupteur(String type, boolean isProg) {

        Optional<Interrupteur> interrupteurOptional = interrupteurRepository.findByType(type);
        Interrupteur newInterrupteur = new Interrupteur();
        if (interrupteurOptional.isPresent()) {
            Interrupteur oldInterrupteur = interrupteurOptional.get();
            newInterrupteur.setEtat(oldInterrupteur.isEtat());
            newInterrupteur.setId(oldInterrupteur.getId());
            newInterrupteur.setType(oldInterrupteur.getType());
            newInterrupteur.setProg(isProg);
        } else {
            newInterrupteur = new Interrupteur();
            newInterrupteur.setEtat(false);
            newInterrupteur.setProg(isProg);
            newInterrupteur.setType(type);

        }
        interrupteurRepository.save(newInterrupteur);


        return newInterrupteur;

    }

    ///////////////////////////////////////Chauffage////////////////////////////////////////////////////////////////
    public void InterrupterManuelleChauffage(String type) {
        Terrarium terrariumCourant = terrariumService.getCurrentParameter();
        if (getControleInterrupteur(type).isEtat()) {
            log.info("Votre chauffage est allume, il est controlé manuellement,  la temperature courante est " + terrariumCourant.getTemperature() + "°C");
        } else {
            log.info("Votre chauffage est eteint, il est controlé manuellement,  la temperature courante est " + terrariumCourant.getTemperature() + "°C");
        }
    }

    public void InitInterrupterChauffage() {
        Interrupteur interrupteur = getControleInterrupteur("chauffage");

        if (interrupteur.isEtat()) {
            try {
                log.info("START : Lancer le script du chauffage pour l'allumer");
                Process pr = Runtime.getRuntime().exec("python ../python/chauffage_test.py 1");

                BufferedReader in = new BufferedReader(new
                        InputStreamReader(pr.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println(line);
                }
                in.close();
                pr.waitFor();
                log.info("END : On a reussi à l'allumer");
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    /**
     * changer l'etat du chauffage en mode manuel
     *
     * @param etat
     * @return
     */
    public Interrupteur ChangeInterrupterManuelleChauffage(boolean etat) {
        Interrupteur interrupteur = getControleInterrupteur("chauffage");
        if (!interrupteur.isProg()) {
            if (etat != interrupteur.isEtat()) {
                interrupteur.setEtat(etat);
                if (etat) {
                    try {
                        log.info("START : Lancer le script du chauffage pour l'allumer");
                        Process pr = Runtime.getRuntime().exec("python ../python/chauffage_test.py 1");

                        BufferedReader in = new BufferedReader(new
                                InputStreamReader(pr.getInputStream()));
                        String line;
                        while ((line = in.readLine()) != null) {
                            System.out.println(line);
                        }
                        in.close();
                        pr.waitFor();
                        log.info("END : On a reussi à l'allumer");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else {
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


                }

            }
        } else {
            throw new ParameterErrorException(ResultEnum.Facon_Controler);

        }

        interrupteurRepository.save(interrupteur);
        return interrupteur;
    }


    public Interrupteur InterrupterProgrammableChauffage(String type) {
        Interrupteur chauffageInterrupteur = getControleInterrupteur(type);
        Terrarium terrarium_current = terrariumService.getCurrentParameter();
        Date currentTime = terrarium_current.getCreateTime();
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentTime);
        int month = cal.get(Calendar.MONTH) + 1;
        int heure = cal.get(Calendar.HOUR_OF_DAY);
        double currentTemperature = terrarium_current.getTemperature();
        Boolean etat = chauffageInterrupteur.isEtat();
        if (etat) {
            log.info("Votre chauffage est allume, il est controlé programmement");
        } else {
            log.info("Votre chauffage est eteint，il est controlé programmement");
        }
        log.info("La temperature courante est :" + currentTemperature);
        List<Chauffage> chauffages = chauffageRepositary.findAll();
        if (chauffages.size() == 0) {

            log.info("Vous configurez pas encore le chauffage. On change pas l'etat du  chauffage ");

        } else {
            Chauffage chauffageConfigurationCourant = new Chauffage();
            boolean isChauffageConfiguration = false;
            for (Chauffage c : chauffages) {
                if (c.getMoisDebut() <= month && c.getMoisFin() >= month && c.getHeureDebut() <= heure && heure <= c.getHeureFin()) {
                    chauffageConfigurationCourant = c;
                    isChauffageConfiguration = true;
                    break;
                }
            }
            if (isChauffageConfiguration) {
                log.info("Le MAXIMUM de temperautre est " + chauffageConfigurationCourant.getMax());
                log.info("Le MINIMUM de temperautre est " + chauffageConfigurationCourant.getMin());
                if (chauffageConfigurationCourant.getMax() < currentTemperature) {
                    if (etat) {
                        chauffageInterrupteur.setEtat(false);
                        interrupteurRepository.save(chauffageInterrupteur);
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

                    } else {
                        log.info("Le chauffage est deja eteint, on ne change pas l'état du chauffage");
                    }

                } else if (chauffageConfigurationCourant.getMin() > currentTemperature) {
                    if (!etat) {
                        chauffageInterrupteur.setEtat(true);
                        interrupteurRepository.save(chauffageInterrupteur);
                        log.info("Trop froid, allumer le chauffage");

                        try {
                            log.info("START : Lancer le script de chauffage pour allumer le chauffage");
                            Process pr = Runtime.getRuntime().exec("python ../python/chauffage_test.py 1");

                            BufferedReader in = new BufferedReader(new
                                    InputStreamReader(pr.getInputStream()));
                            String line;
                            while ((line = in.readLine()) != null) {
                                System.out.println(line);
                            }
                            in.close();
                            pr.waitFor();
                            log.info("END : on a reussi à allumer le chauffage");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        log.info("Le chauffage est deja allume, on ne change pas l'etat du chauffage");
                    }
                } else if (currentTemperature <= chauffageConfigurationCourant.getMax() && currentTemperature >= chauffageConfigurationCourant.getMin()) {

                    log.info("La temperature a l'air correcte manintenant, On eteindre du chauffage ");
                    try {
                        chauffageInterrupteur.setEtat(false);
                        interrupteurRepository.save(chauffageInterrupteur);
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
                }


            } else {
                if (chauffageInterrupteur.isEtat()) {
                    chauffageInterrupteur.setEtat(false);
                    interrupteurRepository.save(chauffageInterrupteur);
                    log.info("Le temps ne correspond pas à la configuration. On eteindre du chauffage ");
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
                } else {
                    log.info("Le temps ne correspond pas à la configuration. On ne change pas l'etat du  chauffage, le chauffage reste eteint");


                }


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


        return chauffageInterrupteur;

    }


    ///////////////////////////////////////Lumiere////////////////////////////////////////////////////////////////
    public void InterrupterManuelleLumiere(String type) {
        if (getControleInterrupteur(type).isEtat()) {
            log.info("Votre lumiere est allume, il est controlé manuellement");
        } else {
            log.info("Votre lumiere est eteint, il est controlé manuellement.");
        }
    }

    public void InitInterrupterLumiere() {
        Interrupteur interrupteur = getControleInterrupteur("lumiere");
        if (interrupteur.isEtat()) {
            try {
                log.info("START : Lancer le script de la lumiere pour allumer la lumiere");
                Process pr = Runtime.getRuntime().exec("python ../python/lumiere_test.py 1");

                BufferedReader in = new BufferedReader(new
                        InputStreamReader(pr.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println(line);
                }
                in.close();
                pr.waitFor();
                log.info("END : on a reussi à allumer la lumiere");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Interrupteur ChangeInterrupterManuelleLumiere(boolean etat) {

        Interrupteur interrupteur = getControleInterrupteur("lumiere");
        if (!interrupteur.isProg()) {
            if (etat != interrupteur.isEtat()) {
                interrupteur.setEtat(etat);
                if (etat) {
                    try {
                        log.info("START : Lancer le script de la lumiere pour allumer la lumiere");
                        Process pr = Runtime.getRuntime().exec("python ../python/lumiere_test.py 1");

                        BufferedReader in = new BufferedReader(new
                                InputStreamReader(pr.getInputStream()));
                        String line;
                        while ((line = in.readLine()) != null) {
                            System.out.println(line);
                        }
                        in.close();
                        pr.waitFor();
                        log.info("END : on a reussi à allumer le lumiere");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    try {
                        log.info("START : Lancer le script de la lumiere pour eteindre la lumiere");
                        Process pr = Runtime.getRuntime().exec("python ../python/lumiere_test.py 0");

                        BufferedReader in = new BufferedReader(new
                                InputStreamReader(pr.getInputStream()));
                        String line;
                        while ((line = in.readLine()) != null) {
                            System.out.println(line);
                        }
                        in.close();
                        pr.waitFor();
                        log.info("END : on a reussi à eteindre le lumiere");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }


            } else {
                log.info("vous changez pas l'etat de lumiere");


            }
        } else {
            throw new ParameterErrorException(ResultEnum.Facon_Controler);

        }


        interrupteurRepository.save(interrupteur);

        return interrupteur;

    }


    public Interrupteur InterrupterProgrammableLumiere(String type) {
        Interrupteur lumiereInterrupteur = getControleInterrupteur(type);
        Date time_current = new Date();
        time_current.getTime();
        Calendar cal = Calendar.getInstance();
        cal.setTime(time_current);
        int month = cal.get(Calendar.MONTH) + 1;
//        log.info("月份" + month);
        int heure = cal.get(Calendar.HOUR_OF_DAY);
        //log.info("月份 " + month + " 小时 " + heure);
        List<Lumiere> lumieres = lumiereRepositary.findAll();
        if (lumieres.size() == 0) {

            log.info("Vous configurez pas encore la lumiere. On change pas l'etat du  lumiere ");

        } else {
            boolean isConfigurationLumiere = false;
            for (Lumiere c : lumieres) {
                if (c.getMoisDebut() <= month && c.getMoisFin() >= month && c.getHeureDebut() <= heure && heure <= c.getHeureFin()) {
                    isConfigurationLumiere = true;
                    break;
                }
            }

            if (isConfigurationLumiere) {
                if (lumiereInterrupteur.isEtat()) {
                    log.info("la lumiere est deja allume,il est controler programmable");
                } else {
                    lumiereInterrupteur.setEtat(true);
                    interrupteurRepository.save(lumiereInterrupteur);
                    try {
                        log.info("START : Lancer le script de la lumiere pour allumer la lumiere");
                        Process pr = Runtime.getRuntime().exec("python ../python/lumiere_test.py 1");

                        BufferedReader in = new BufferedReader(new
                                InputStreamReader(pr.getInputStream()));
                        String line;
                        while ((line = in.readLine()) != null) {
                            System.out.println(line);
                        }
                        in.close();
                        pr.waitFor();
                        log.info("END : on a reussi à allumer le lumiere");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    log.info("On a allume la lumiere");
                }

            } else {
                if (!lumiereInterrupteur.isEtat()) {
                    log.info("la lumiere est deja ferme,il est controler programmable");
                } else {
                    lumiereInterrupteur.setEtat(false);
                    interrupteurRepository.save(lumiereInterrupteur);
                    try {
                        log.info("START : Lancer le script de la lumiere pour eteindre la lumiere");
                        Process pr = Runtime.getRuntime().exec("python ../python/lumiere_test.py 0");

                        BufferedReader in = new BufferedReader(new
                                InputStreamReader(pr.getInputStream()));
                        String line;
                        while ((line = in.readLine()) != null) {
                            System.out.println(line);
                        }
                        in.close();
                        pr.waitFor();
                        log.info("END : on a reussi à eteindre le lumiere");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    log.info("On a ferme la lumiere");
                }

            }
        }


        return lumiereInterrupteur;

    }


}
