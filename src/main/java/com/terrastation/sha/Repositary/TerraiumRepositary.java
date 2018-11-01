package com.terrastation.sha.Repositary;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import com.terrastation.sha.Entity.Terraium;
import org.springframework.data.jpa.repository.Query;

public interface TerraiumRepositary extends JpaRepository<Terraium,Integer> {
    @Query(value = "select *  from runoob_db.terraium where (select max(create_time) from runoob_db.terraium )=create_time ", nativeQuery = true)
    public Terraium findCurrentTemperature();

    @Query(value = "SELECT * FROM runoob_db.terraium order by create_time desc limit ?1 ; ", nativeQuery = true)
     public List<Terraium> findCurrentTemperatures(Integer quantity);

    @Query(value="select * from (SELECT * FROM runoob_db.terraium order by create_time desc limit ?1) as currenctTemp1 where (select max(temperature) from (SELECT * FROM runoob_db.terraium order by create_time desc limit ?1) as currentTemp2)=temperature", nativeQuery = true)
     public Terraium findMaxTemperatures(Integer quantity);

    @Query(value="select * from (SELECT * FROM runoob_db.terraium order by create_time desc limit ?1) as currenctTemp1 where (select min(temperature) from (SELECT * FROM runoob_db.terraium order by create_time desc limit ?1) as currentTemp2)=temperature", nativeQuery = true)
    public Terraium findMinTemperatures(Integer quantity);

}
