package com.terrastation.sha.Service;


import com.terrastation.sha.Entity.Interrupteur;

public interface InterrupteurService {
    public Interrupteur InterrupterProgrammable();
    public boolean getControleInterrupteur();
    public Interrupteur changeControleInterrupteur(boolean isProg) ;

}
