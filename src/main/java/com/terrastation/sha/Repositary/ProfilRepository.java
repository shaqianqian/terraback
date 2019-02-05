package com.terrastation.sha.Repositary;


import com.terrastation.sha.Entity.Chauffage;
import com.terrastation.sha.Entity.Profil;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfilRepository extends JpaRepository<Profil,Integer> {

}