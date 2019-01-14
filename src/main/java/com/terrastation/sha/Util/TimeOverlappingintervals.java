package com.terrastation.sha.Util;

import com.terrastation.sha.Entity.Chauffage;
import com.terrastation.sha.Entity.Lumiere;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class TimeOverlappingintervals {

    public static boolean analyeMoisChauffage(List<Chauffage> chauffages) {

        for (int i = 0; i < chauffages.size() - 1; i++) {
            for (int j = i + 1; j < chauffages.size(); j++) {
                if (chauffages.get(i).getMoisDebut() == chauffages.get(j).getMoisDebut() && chauffages.get(i).getMoisFin() == chauffages.get(j).getMoisFin()) {
                    if (Math.max(chauffages.get(i).getHeureDebut(), chauffages.get(j).getHeureDebut()) <= Math.min(chauffages.get(i).getHeureFin(), chauffages.get(j).getHeureFin())) {
                        return false;
                    }
                } else if (Math.max(chauffages.get(i).getMoisDebut(), chauffages.get(j).getMoisDebut()) <= Math.min(chauffages.get(i).getMoisFin(), chauffages.get(j).getMoisFin())) {
                    return false;
                }

            }

        }
        return true;
    }

    public static boolean analyeMoisTouteAnneeChauffage(List<Chauffage> chauffages) {

        for (int i = 0; i < chauffages.size() - 1; i++) {
            for (int j = i + 1; j < chauffages.size(); j++) {
                if (Math.max(chauffages.get(i).getHeureDebut(), chauffages.get(j).getHeureDebut()) <= Math.min(chauffages.get(i).getHeureFin(), chauffages.get(j).getHeureFin())) {
                    return false;
                }
            }

        }
        return true;
    }
    public static boolean analyeMoisLumiere(List<Lumiere> Lumieres) {

        for (int i = 0; i < Lumieres.size() - 1; i++) {
            for (int j = i + 1; j < Lumieres.size(); j++) {
                if (Lumieres.get(i).getMoisDebut() == Lumieres.get(j).getMoisDebut() && Lumieres.get(i).getMoisFin() == Lumieres.get(j).getMoisFin()) {
                    if (Math.max(Lumieres.get(i).getHeureDebut(), Lumieres.get(j).getHeureDebut()) <= Math.min(Lumieres.get(i).getHeureFin(), Lumieres.get(j).getHeureFin())) {
                        return false;
                    }
                } else if (Math.max(Lumieres.get(i).getMoisDebut(), Lumieres.get(j).getMoisDebut()) <= Math.min(Lumieres.get(i).getMoisFin(), Lumieres.get(j).getMoisFin())) {
                    return false;
                }

            }

        }
        return true;
    }
    public static boolean analyeMoisTouteAnneeLumiere(List<Lumiere> Lumieres) {

        for (int i = 0; i < Lumieres.size() - 1; i++) {
            for (int j = i + 1; j < Lumieres.size(); j++) {
                if (Math.max(Lumieres.get(i).getHeureDebut(), Lumieres.get(j).getHeureDebut()) <= Math.min(Lumieres.get(i).getHeureFin(), Lumieres.get(j).getHeureFin())) {
                    return false;
                }
            }

        }
        return true;
    }

}