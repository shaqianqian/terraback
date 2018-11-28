package com.terrastation.sha.Service;

import com.terrastation.sha.Entity.Terrarium;
import com.terrastation.sha.VO.TerrariumsVO;

import java.util.List;
import java.util.Optional;

public interface TerrariumService {

    public TerrariumsVO GetCurrentHumiditesVO(int quantity);
    public TerrariumsVO GetCurrentTemperaturesVO(int quantity);
    public List<Terrarium> getCurrentParameters(int quantity);
    public Terrarium getCurrentParameter();
    public List<Terrarium> findAll();
    public Optional<Terrarium> findById(Integer Id);
    public Terrarium save(Terrarium t);
    public void delete(Terrarium t);
    public int getRowQuantity();


}
