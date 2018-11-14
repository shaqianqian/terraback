package com.terrastation.sha.Service;

import com.terrastation.sha.VO.TerraiumsGenereVO;

public interface TerraiumGenereService {
    public TerraiumsGenereVO GetCurrentHumiditesVO(int quantite);
    public TerraiumsGenereVO GetCurrentTemperaturesVO(int quantite);
}
