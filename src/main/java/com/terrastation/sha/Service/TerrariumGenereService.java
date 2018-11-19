package com.terrastation.sha.Service;

import com.terrastation.sha.VO.TerrariumsGenereVO;

public interface TerrariumGenereService {
    public TerrariumsGenereVO GetCurrentHumiditesVO(int quantite);
    public TerrariumsGenereVO GetCurrentTemperaturesVO(int quantite);
}
