package com.terrastation.sha.Service;


import com.terrastation.sha.Entity.Interrupteur;

public interface InterrupteurService {
    public Interrupteur InterrupterProgrammable(String type);
    public Interrupteur changeControleInterrupteur(String type, boolean isProg) ;
    public Interrupteur getControleInterrupteur(String type);
    public void InterrupterManuelle(String type);

}
