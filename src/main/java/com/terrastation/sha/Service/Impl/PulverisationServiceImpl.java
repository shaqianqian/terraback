package com.terrastation.sha.Service.Impl;

import com.terrastation.sha.Entity.Chauffage;
import com.terrastation.sha.Entity.Pulverisation;
import com.terrastation.sha.Entity.PulverisationInterrupteur;
import com.terrastation.sha.Entity.Terrarium;
import com.terrastation.sha.Repositary.PulverisationInterrupeurRepository;
import com.terrastation.sha.Repositary.PulverisationRepository;
import com.terrastation.sha.Repositary.TerrariumRepositary;
import com.terrastation.sha.Service.PulverisationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class PulverisationServiceImpl implements PulverisationService {
    @Autowired
    private PulverisationRepository pulverisationRepository;
    @Autowired
    private TerrariumRepositary terrariumRepositary;
    @Autowired
    private PulverisationInterrupeurRepository pulverisationInterrupeurRepository;


    public void pulverisationModeHygrometrie() {
        Terrarium terrarium_current = terrariumRepositary.getCurrentParameter();

        if (!pulverisationRepository.findByMode("hygrometrie").isPresent()) {
            log.info("vous avez pas encore configurez le pulverisation en mode hygrometrie ");

        } else {
            List<Pulverisation> pulverisationList = pulverisationRepository.findByMode("hygrometrie").get();


            log.info("vous controlez le pulverisation en mode hygrometrie ,Humidite courant est " + terrarium_current.getHumidite());

            Date currentTime = terrarium_current.getCreateTime();
            Calendar cal = Calendar.getInstance();
            cal.setTime(currentTime);
            int month = cal.get(Calendar.MONTH) + 1;
            int heure = cal.get(Calendar.HOUR_OF_DAY);
            Pulverisation pulverisation = new Pulverisation();
            boolean isConfiguration = false;
            for (Pulverisation p : pulverisationList) {
                if (p.getMoisDebut() <= month && p.getMoisFin() >= month && p.getHeureDebut() <= heure && heure <= p.getHeureFin()) {
                    pulverisation = p;
                    isConfiguration = true;
                    break;
                }
            }
            if (isConfiguration) {
                if ((terrarium_current.getHumidite() < pulverisation.getTaux_hygrometrie_min()) && isConfiguration) {
                    try {
                        log.info("Le min taux de hygrometrie est "+ pulverisation.getTaux_hygrometrie_min()+ ", Votre terrarium n'est pas assez humide, lancer la pulverisation, la duree est " + pulverisation.getDuree_hygrometrie());
                        log.info("START : Lancer le script du pulverisation");
                        Process pr = Runtime.getRuntime().exec("python ../python/pulverisation_test.py " + pulverisation.getDuree_hygrometrie());

                        BufferedReader in = new BufferedReader(new
                                InputStreamReader(pr.getInputStream()));
                        String line;
                        while ((line = in.readLine()) != null) {
                            System.out.println(line);
                        }
                        in.close();
                        pr.waitFor();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {

                    log.info("La humidite a l'air correcte maintenant, on ne lance pas la pulverisation");
                }
            } else {
                log.info("Le temps ne correspond pas à la configuration. On ne lance pas la pulverisation");

            }

        }


    }

}