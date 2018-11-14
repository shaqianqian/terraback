package com.terrastation.sha.Service;

import com.terrastation.sha.Entity.Terraium;
import com.terrastation.sha.VO.TerraiumsGenereVO;
import com.terrastation.sha.VO.TerraiumsVO;

import java.util.List;
import java.util.Optional;

public interface TerraiumService {

    public TerraiumsVO GetCurrentHumiditesVO(int quantity);
    public TerraiumsVO GetCurrentTemperaturesVO(int quantity);
    public List<Terraium> getCurrentParametres(int quantity);
    public List<Terraium> findAll();
    public Optional<Terraium> findById(Integer Id);
    public Terraium save(Terraium t);
    public void delete(Terraium t);
    public int getRowQuantity();


}
