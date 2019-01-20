package com.terrastation.sha.Repositary;
import com.terrastation.sha.Entity.Interrupteur;
import com.terrastation.sha.Entity.Pulverisation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PulverisationRepository extends JpaRepository<Pulverisation,Integer> {
    Optional<List<Pulverisation>> findByMode(String type);


}