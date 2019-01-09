package com.terrastation.sha.Service.Impl;
import com.terrastation.sha.Entity.Pulverisation;
import com.terrastation.sha.Entity.Terrarium;
import com.terrastation.sha.Repositary.PulverisationRepository;
import com.terrastation.sha.Repositary.TerrariumRepositary;
import com.terrastation.sha.Service.PulverisationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Slf4j
@Service
public class PulverisationServiceImpl implements PulverisationService {
    @Autowired
    private PulverisationRepository pulverisationRepository;
    @Autowired
    private TerrariumRepositary terrariumRepositary;

    public void pulverisationModeHygrometrie(){
        Terrarium terrarium_current = terrariumRepositary.getCurrentParameter();

        Pulverisation pulverisation = pulverisationRepository.findAll().get(0);
        if (pulverisation.getMode() == null || pulverisation.getMode().isEmpty()) {

            log.info("vous avez pas encore configurez la mode de pulverisation");

        } else if (pulverisation.getMode().equals("hygrometrie")) {

            log.info("vous controlez le pulverisation en mode hygrometrie ,Humidite courant est " + terrarium_current.getHumidite());
            if(pulverisation.getDuree_hygrometrie()==0){
                log.info("vous avez pas encore configurez la mode hygrometrie de pulverisation");

            }
            else if (terrarium_current.getHumidite() < pulverisation.getTaux_hygrometrie_min()) {
                try {
                    log.info("Votre terrarium n'est pas assez humide, lancer la pulverisation, la duree est "+pulverisation.getDuree_hygrometrie());
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


            }


        }


    }

}