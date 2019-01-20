package com.terrastation.sha.Service;


import com.terrastation.sha.Entity.Interrupteur;
import com.terrastation.sha.Entity.Terrarium;
import com.terrastation.sha.VO.ResultVO;

import java.util.List;

public interface AlarmeService {

    public List<Terrarium> alarmeTemperature();
    public List<Terrarium> alarmeHygrometrie();

}
