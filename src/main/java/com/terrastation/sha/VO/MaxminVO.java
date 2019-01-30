package com.terrastation.sha.VO;

import lombok.Data;

@Data
public class MaxminVO {
    double max;

    public MaxminVO(double max, double min) {
        this.max = max;
        this.min = min;
    }

    public MaxminVO() {
    }

    double min;
}
