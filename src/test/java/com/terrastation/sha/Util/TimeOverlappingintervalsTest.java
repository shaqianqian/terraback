package com.terrastation.sha.Util;

import com.terrastation.sha.Entity.Chauffage;
import com.terrastation.sha.Entity.Lumiere;
import com.terrastation.sha.Entity.Pulverisation;
import com.terrastation.sha.Exception.ParameterErrorException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TimeOverlappingintervalsTest {

    @Test
    public void analyeMoisErrorChauffage() {

        Chauffage c1=new Chauffage();
        Chauffage c2=new Chauffage();
        c1.setMoisDebut(1);
        c1.setMoisFin(5);
        c2.setMoisDebut(2);
        c2.setMoisFin(5);
        List<Chauffage> chauffageList=new ArrayList<Chauffage>();
        chauffageList.add(c1);
        chauffageList.add(c2);
        boolean resulat=TimeOverlappingintervals.analyeMoisChauffage(chauffageList);
        assertFalse(resulat);
    }
    @Test
    public void analyeMoisCorrectChauffage() {

        Chauffage c1=new Chauffage();
        Chauffage c2=new Chauffage();
        c1.setMoisDebut(1);
        c1.setMoisFin(5);
        c2.setMoisDebut(6);
        c2.setMoisFin(9);
        List<Chauffage> chauffageList=new ArrayList<Chauffage>();
        chauffageList.add(c1);
        chauffageList.add(c2);
        boolean resulat=TimeOverlappingintervals.analyeMoisChauffage(chauffageList);
        assertTrue(resulat);
    }

    @Test
    public void analyeMoisTouteAnneeChauffageError() {
        Chauffage c1=new Chauffage();
        Chauffage c2=new Chauffage();
        c1.setHeureDebut(1);
        c1.setHeureFin(5);
        c2.setHeureDebut(2);
        c2.setHeureFin(5);
        List<Chauffage> chauffageList=new ArrayList<Chauffage>();
        chauffageList.add(c1);
        chauffageList.add(c2);
        boolean resulat=TimeOverlappingintervals.analyeMoisChauffage(chauffageList);
        assertFalse(resulat);
    }

    @Test
    public void analyeMoisTouteAnneeChauffage() {
        Chauffage c1=new Chauffage();
        Chauffage c2=new Chauffage();
        c1.setHeureDebut(1);
        c1.setHeureFin(5);
        c2.setHeureDebut(6);
        c2.setHeureFin(12);
        List<Chauffage> chauffageList=new ArrayList<Chauffage>();
        chauffageList.add(c1);
        chauffageList.add(c2);
        boolean resulat=TimeOverlappingintervals.analyeMoisChauffage(chauffageList);
        assertTrue(resulat);
    }

    @Test
    public void analyeMoisLumiereError() {

        Lumiere c1=new Lumiere();
        Lumiere c2=new Lumiere();
        c1.setMoisDebut(1);
        c1.setMoisFin(5);
        c2.setMoisDebut(2);
        c2.setMoisFin(5);
        List<Lumiere> chauffageList=new ArrayList<Lumiere>();
        chauffageList.add(c1);
        chauffageList.add(c2);
        boolean resulat=TimeOverlappingintervals.analyeMoisLumiere(chauffageList);
        assertFalse(resulat);
    }
    @Test
    public void analyeMoisLumiere() {

        Lumiere c1=new Lumiere();
        Lumiere c2=new Lumiere();
        c1.setMoisDebut(1);
        c1.setMoisFin(5);
        c2.setMoisDebut(7);
        c2.setMoisFin(8);
        List<Lumiere> chauffageList=new ArrayList<Lumiere>();
        chauffageList.add(c1);
        chauffageList.add(c2);
        boolean resulat=TimeOverlappingintervals.analyeMoisLumiere(chauffageList);
        assertTrue(resulat);
    }

    @Test
    public void analyeMoisTouteAnneeLumiere() {
        Lumiere c1=new Lumiere();
        Lumiere c2=new Lumiere();
        c1.setHeureDebut(1);
        c1.setHeureFin(5);
        c2.setHeureDebut(7);
        c2.setHeureFin(8);
        List<Lumiere> chauffageList=new ArrayList<Lumiere>();
        chauffageList.add(c1);
        chauffageList.add(c2);
        boolean resulat=TimeOverlappingintervals.analyeMoisLumiere(chauffageList);
        assertTrue(resulat);
    }
    @Test
    public void analyeMoisTouteAnneeLumiereError() {
        Lumiere c1=new Lumiere();
        Lumiere c2=new Lumiere();
        c1.setHeureDebut(1);
        c1.setHeureFin(5);
        c2.setHeureDebut(4);
        c2.setHeureFin(8);
        List<Lumiere> chauffageList=new ArrayList<Lumiere>();
        chauffageList.add(c1);
        chauffageList.add(c2);
        boolean resulat=TimeOverlappingintervals.analyeMoisLumiere(chauffageList);
        assertFalse(resulat);
    }

    @Test
    public void analyeMoisPulverisationHygrometrieError() {
        Pulverisation c1=new Pulverisation();
        Pulverisation  c2=new Pulverisation ();
        c1.setHeureDebut(1);
        c1.setHeureFin(5);
        c2.setHeureDebut(4);
        c2.setHeureFin(8);
        List<Pulverisation > chauffageList=new ArrayList<Pulverisation >();
        chauffageList.add(c1);
        chauffageList.add(c2);
        boolean resulat=TimeOverlappingintervals.analyeMoisPulverisationHygrometrie(chauffageList);
        assertFalse(resulat);
    }
    @Test
    public void analyeMoisPulverisationHygrometrie() {
        Pulverisation c1=new Pulverisation();
        Pulverisation  c2=new Pulverisation ();
        c1.setHeureDebut(1);
        c1.setHeureFin(5);
        c2.setHeureDebut(6);
        c2.setHeureFin(8);
        List<Pulverisation > chauffageList=new ArrayList<Pulverisation >();
        chauffageList.add(c1);
        chauffageList.add(c2);
        boolean resulat=TimeOverlappingintervals.analyeMoisPulverisationHygrometrie(chauffageList);
        assertTrue(resulat);
    }

    @Test
    public void analyeMoisTouteAnneePulverisationHygrometrieError() {
        Pulverisation c1=new Pulverisation();
        Pulverisation  c2=new Pulverisation ();
        c1.setHeureDebut(1);
        c1.setHeureFin(5);
        c2.setHeureDebut(4);
        c2.setHeureFin(8);
        List<Pulverisation > chauffageList=new ArrayList<Pulverisation >();
        chauffageList.add(c1);
        chauffageList.add(c2);
        boolean resulat=TimeOverlappingintervals.analyeMoisPulverisationHygrometrie(chauffageList);
        assertFalse(resulat);
    }
    @Test
    public void analyeMoisTouteAnneePulverisationHygrometrie() {
        Pulverisation c1=new Pulverisation();
        Pulverisation  c2=new Pulverisation ();
        c1.setHeureDebut(1);
        c1.setHeureFin(5);
        c2.setHeureDebut(6);
        c2.setHeureFin(8);
        List<Pulverisation > chauffageList=new ArrayList<Pulverisation >();
        chauffageList.add(c1);
        chauffageList.add(c2);
        boolean resulat=TimeOverlappingintervals.analyeMoisPulverisationHygrometrie(chauffageList);
        assertTrue(resulat);
    }

}