package com.terrastation.sha.Service;

import com.terrastation.sha.Entity.Terrarium;
import com.terrastation.sha.VO.TerrariumsGenereVO;

import java.util.List;

public interface TerrariumGenereService {
    public TerrariumsGenereVO GetCurrentHumiditesVO(int quantite);
    public TerrariumsGenereVO GetCurrentTemperaturesVO(int quantite);
    public List<Terrarium> getDonneeUneSemaine();
    public TerrariumsGenereVO GetUneSemaineTemperaturesVO();
    public TerrariumsGenereVO GetUneSemaineHumiditesVO();

    public List<Terrarium> getDonneeUnMois();
    public TerrariumsGenereVO GetUnMoisTemperaturesVO();
    public TerrariumsGenereVO GetUnMoisHumiditesVO();

}
