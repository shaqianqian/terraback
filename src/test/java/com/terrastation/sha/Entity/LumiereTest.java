package com.terrastation.sha.Entity;

import com.terrastation.sha.Exception.ParameterErrorException;
import org.junit.Test;

import static org.junit.Assert.*;

public class LumiereTest {

    @Test(expected=ParameterErrorException.class)
    public void setMoisFinBiggerThan12Test() {

        Lumiere c=new Lumiere();
        c.setMoisFin(13);


    }
    @Test(expected=ParameterErrorException.class)
    public void setMoisFinSmallerThan0Test() {

        Lumiere c=new Lumiere();
        c.setMoisFin(0);

    }
    @Test(expected=ParameterErrorException.class)
    public void setMoisDebutSmallerThan0Test() {

        Lumiere c=new Lumiere();
        c.setMoisDebut(0);

    }
    @Test(expected=ParameterErrorException.class)
    public void setHeureFinBiggerThan24Test() {
        Lumiere c=new Lumiere();
        c.setHeureFin(25);
    }
    @Test(expected=ParameterErrorException.class)
    public void setHeureFinSmallerThan0Test() {
        Lumiere c=new Lumiere();
        c.setHeureFin(-2);
    }
    @Test(expected=ParameterErrorException.class)
    public void setHeureDebutmallerThan0Test() {
        Lumiere c=new Lumiere();
        c.setHeureDebut(-2);
    }
    @Test
    public void setHeureFinTest() {
        Lumiere c=new Lumiere();
        c.setHeureFin(10);
        assertEquals(10,c.getHeureFin());
    }
    @Test
    public void setHeureDebutTest() {
        Lumiere c=new Lumiere();
        c.setHeureDebut(10);
        assertEquals(10,c.getHeureDebut());
    }
}