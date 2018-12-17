package com.terrastation.sha.Service;


import com.terrastation.sha.Entity.Interrupteur;

public interface InterrupteurService {
    public Interrupteur InterrupterProgrammableChauffage(String type);
    public Interrupteur changeControleInterrupteur(String type, boolean isProg) ;
    public Interrupteur getControleInterrupteur(String type);
    public void InterrupterManuelleChauffage(String type);
    public void InterrupterManuelleLumiere(String type);
    public Interrupteur InterrupterProgrammableLumiere(String type);
    public Interrupteur ChangeInterrupterManuelleLumiere(boolean etat);
    public Interrupteur ChangeInterrupterManuelleChauffage(boolean etat);
}
