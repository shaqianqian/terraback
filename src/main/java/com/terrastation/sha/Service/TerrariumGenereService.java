package com.terrastation.sha.Service;

import com.terrastation.sha.Entity.Terrarium;
import com.terrastation.sha.VO.TerrariumsGenereVO;

import java.util.List;

public interface TerrariumGenereService {
    public TerrariumsGenereVO GetCurrentHumiditesVO(int quantite);
    public TerrariumsGenereVO GetCurrentTemperaturesVO(int quantite);
    public List<Terrarium> getDonneeUneSemaine(int quantite);
    public TerrariumsGenereVO GetUneSemaineTemperaturesVO(int quantite);
    public TerrariumsGenereVO GetUneSemaineHumiditesVO(int quantite);

    public List<Terrarium> getDonneeUnMois(int quantite);
    public TerrariumsGenereVO GetUnMoisTemperaturesVO(int quantite);
    public TerrariumsGenereVO GetUnMoisHumiditesVO(int quantite);

}
