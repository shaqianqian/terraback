package com.terrastation.sha.Entity;

import org.junit.Test;

import static org.junit.Assert.*;

public class InterrupteurTest {

    @Test
    public void getType() {
        Interrupteur interrupteur=new Interrupteur();
        interrupteur.setType("chauffage");
        assertEquals(interrupteur.getType(),"chauffage");
    }

    @Test
    public void isEtat() {
        Interrupteur interrupteur=new Interrupteur();
        interrupteur.setEtat(false);
        assertFalse(interrupteur.isEtat());
    }

    @Test
    public void isProg() {
        Interrupteur interrupteur=new Interrupteur();
        interrupteur.setProg(false);
        assertFalse(interrupteur.isProg());
    }
}