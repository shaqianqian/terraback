package com.terrastation.sha.Controller;

import com.terrastation.sha.Entity.Terraium;
import com.terrastation.sha.Exception.TerraiumException;
import com.terrastation.sha.Repositary.TerraiumRepositary;
import com.terrastation.sha.Util.ResultUtil;
import com.terrastation.sha.VO.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController

public class HumiditeController {
    Logger log = LoggerFactory.getLogger(HumiditeController.class);


    @Autowired
    private TerraiumRepositary terraiumRepositary;



    @RequestMapping(value = "/terraium/humidite/getCurrentHumiditesVO", method = RequestMethod.GET)
    public ResultVO<TerraiumsVO> getCurrentHumiditesVO() {
        List<Terraium> terraiumList = terraiumRepositary.findCurrentTemperatures(6);
        Collections.reverse(terraiumList);
        List<TerraiumVO> humiditeVOList = new ArrayList<TerraiumVO>();
        for (Terraium t : terraiumList) {
            TerraiumVO humiditeVO = new TerraiumVO();
            humiditeVO.setId(t.getId());
            humiditeVO.setValeur(t.getHumidite());
            humiditeVO.setCreateTime(t.getCreateTime());
            humiditeVO.setUpdateTime(t.getUpdateTime());
            humiditeVOList.add(humiditeVO);
            humiditeVO.setSymbol("%");
            humiditeVO.setType("Humidite");

        }
        Terraium hum_max = terraiumRepositary.findMaxHumidites(6);
        TerraiumVO humiditeVO_max = new TerraiumVO();
        humiditeVO_max.setId(hum_max.getId());
        humiditeVO_max.setUpdateTime(hum_max.getUpdateTime());
        humiditeVO_max.setCreateTime(hum_max.getCreateTime());
        humiditeVO_max.setValeur(hum_max.getHumidite());
        humiditeVO_max.setSymbol("%");
        humiditeVO_max.setType("Humidite");

        Terraium hum__min = terraiumRepositary.findMinHumidites(6);
        TerraiumVO humiditeVO_min = new TerraiumVO();
        humiditeVO_min.setId(hum__min.getId());
        humiditeVO_min.setUpdateTime(hum__min.getUpdateTime());
        humiditeVO_min.setCreateTime(hum__min.getCreateTime());
        humiditeVO_min.setValeur(hum__min.getHumidite());
        humiditeVO_min.setSymbol("%");
        humiditeVO_min.setType("Humidite");


        TerraiumsVO humiditesVO=new  TerraiumsVO();
        humiditesVO.setMax(humiditeVO_max);
        humiditesVO.setMin(humiditeVO_min);
        humiditesVO.setTerraiumsVO(humiditeVOList);
        return ResultUtil.success(humiditesVO);

    }

    @ControllerAdvice
    public class ExceptionHandle {
        @ResponseBody
        @ExceptionHandler(value = Exception.class)
        public Object handle(Exception e) {
            if (e instanceof TerraiumException) {
                TerraiumException te = (TerraiumException) e;
                return ResultUtil.error(te.getCode(), te.getMessage());
            } else {
                return ResultUtil.error(-1, "unknown error");
            }
        }


    }
}


