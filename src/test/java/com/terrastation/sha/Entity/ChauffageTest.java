package com.terrastation.sha.Entity;

import com.terrastation.sha.Exception.ParameterErrorException;
import org.junit.Test;

import static org.junit.Assert.*;

public class ChauffageTest {

    @Test(expected=ParameterErrorException.class)
    public void setMoisFinBiggerThan12Test() {

        Chauffage c=new Chauffage();
        c.setMoisFin(13);


    }
    @Test(expected=ParameterErrorException.class)
    public void setMoisFinSmallerThan0Test() {

        Chauffage c=new Chauffage();
        c.setMoisFin(0);

    }

    @Test(expected=ParameterErrorException.class)
    public void setHeureFinBiggerThan24Test() {
        Chauffage c=new Chauffage();
        c.setHeureFin(25);
    }
    @Test(expected=ParameterErrorException.class)
    public void setHeureFinSmallerThan0Test() {
        Chauffage c=new Chauffage();
        c.setHeureFin(-2);
    }
}