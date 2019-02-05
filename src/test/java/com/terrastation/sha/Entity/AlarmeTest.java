package com.terrastation.sha.Entity;

import org.junit.Assert;
import org.junit.Test;

public class AlarmeTest {
    @Test
    public void GetSetTest() {

        Alarme alarme=new Alarme("temperature",20,50,10,50,"test");
        Assert.assertEquals(alarme.getMessage(),"test");

    }




}