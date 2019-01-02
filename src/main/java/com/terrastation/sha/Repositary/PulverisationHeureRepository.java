package com.terrastation.sha.Repositary;
import com.terrastation.sha.Entity.Pulverisation;
import com.terrastation.sha.Entity.Pulverisationheure;
import com.terrastation.sha.Entity.Terrarium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PulverisationHeureRepository extends JpaRepository<Pulverisationheure,Integer> {

    @Query(value="DELETE FROM pulverisationheure WHERE id = ?1", nativeQuery = true)
    public void deleteUselessHeure(Integer id);

}