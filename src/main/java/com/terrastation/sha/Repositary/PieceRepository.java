package com.terrastation.sha.Repositary;


import com.terrastation.sha.Entity.Chauffage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PieceRepository extends JpaRepository<Chauffage,Integer> {

}