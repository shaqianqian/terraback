package com.terrastation.sha.Repositary;


import com.terrastation.sha.Entity.Reptile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface reptileRepository extends JpaRepository<Reptile,Integer> {

}