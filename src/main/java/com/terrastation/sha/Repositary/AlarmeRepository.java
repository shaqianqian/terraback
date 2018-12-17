package com.terrastation.sha.Repositary;


import com.terrastation.sha.Entity.Alarme;
import com.terrastation.sha.Entity.Chauffage;
import com.terrastation.sha.Entity.Terrarium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface AlarmeRepository extends JpaRepository<Alarme,Integer> {



}