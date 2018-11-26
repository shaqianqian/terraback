package com.terrastation.sha.Repositary;

import com.terrastation.sha.Entity.Interrupteur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InterrupteurRepository extends JpaRepository<Interrupteur,Integer> {

    Optional<Interrupteur>findByType(String type);


}