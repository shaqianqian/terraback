package com.terrastation.sha.Repositary;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import com.terrastation.sha.Entity.Terrarium;
import org.springframework.data.jpa.repository.Query;

public interface TerrariumRepositary extends JpaRepository<Terrarium,Integer> {
    @Query(value = "select *  from runoob_db.terrarium where (select max(create_time) from runoob_db.terrarium )=create_time ", nativeQuery = true)
    public List<Terrarium>  getCurrentParameter();


    @Query(value = "SELECT * FROM runoob_db.terrarium order by create_time desc limit ?1 ; ", nativeQuery = true)
     public List<Terrarium> findCurrentParameters(Integer quantity);

    @Query(value="select * from (SELECT * FROM runoob_db.terrarium order by create_time desc limit ?1) as currenctTemp1 where (select max(temperature) from (SELECT * FROM runoob_db.terrarium order by create_time desc limit ?1) as currentTemp2)=temperature", nativeQuery = true)
     public Terrarium findMaxTemperatures(Integer quantity);

    @Query(value="select * from (SELECT * FROM runoob_db.terrarium order by create_time desc limit ?1) as currenctTemp1 where (select min(temperature) from (SELECT * FROM runoob_db.terrarium order by create_time desc limit ?1) as currentTemp2)=temperature", nativeQuery = true)
    public Terrarium findMinTemperatures(Integer quantity);


    @Query(value="select * from (SELECT * FROM runoob_db.terrarium order by create_time desc limit ?1) as currenctTemp1 where (select max(humidite) from (SELECT * FROM runoob_db.terrarium order by create_time desc limit ?1) as currentTemp2)=humidite", nativeQuery = true)
    public Terrarium findMaxHumidites(Integer quantity);

    @Query(value="select * from (SELECT * FROM runoob_db.terrarium order by create_time desc limit ?1) as currenctTemp1 where (select min(humidite) from (SELECT * FROM runoob_db.terrarium order by create_time desc limit ?1) as currentTemp2)=humidite", nativeQuery = true)
    public Terrarium findMinHumidites(Integer quantity);

    @Query(value = "SELECT COUNT(*) FROM terrarium;", nativeQuery = true)
    public int getRowQuantity();

    @Query(value="select * from runoob_db.terrarium where create_time between ?1 and ?2 ;", nativeQuery = true)
    public List<Terrarium> variation(String date1,String date2);
    @Query(value="SELECT * FROM terrarium where create_time>DATE_SUB(CURDATE(), INTERVAL 1 WEEK);", nativeQuery = true)
    public List<Terrarium> donneUneSemaine();
    @Query(value="SELECT * FROM terrarium where create_time>DATE_SUB(CURDATE(), INTERVAL 1 MONTH);", nativeQuery = true)
    public List<Terrarium> donneUnMois();
}
