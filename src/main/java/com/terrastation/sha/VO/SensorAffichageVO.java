package com.terrastation.sha.VO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SensorAffichageVO {

    int id;
    String type;

    public SensorAffichageVO(int id, String type, String symbol) {
        this.id = id;
        this.type = type;
        this.symbol = symbol;
    }

    String symbol;

}
