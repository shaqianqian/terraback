package com.terrastation.sha.repositary;

import com.terrastation.sha.domain.Chauffage;


import org.springframework.data.jpa.repository.JpaRepository;

public interface ChauffageRepository extends JpaRepository<Chauffage,Integer> {

}