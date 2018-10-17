package com.terrastation.sha.repositary;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import com.terrastation.sha.domain.terraium;

public interface terraiumRepositary extends JpaRepository<terraium,Integer> {
    public List<terraium> findByTemperature(Float temperature);


}
