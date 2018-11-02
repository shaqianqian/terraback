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
            humiditeVO.setValue(t.getHumidite());
            humiditeVO.setTime(t.getCreateTime());
            humiditeVOList.add(humiditeVO);


        }
        Terraium hum_max = terraiumRepositary.findMaxHumidites(6);
        TerraiumVO humiditeVO_max = new TerraiumVO();
        humiditeVO_max.setTime(hum_max.getCreateTime());
        humiditeVO_max.setValue(hum_max.getHumidite());


        Terraium hum__min = terraiumRepositary.findMinHumidites(6);
        TerraiumVO humiditeVO_min = new TerraiumVO();
        humiditeVO_min.setTime(hum__min.getCreateTime());
        humiditeVO_min.setValue(hum__min.getHumidite());



        TerraiumsVO humiditesVO=new  TerraiumsVO();
        humiditesVO.setMax(humiditeVO_max);
        humiditesVO.setMin(humiditeVO_min);
        humiditesVO.setSymbol("%");
        humiditesVO.setType("Humidite");
        humiditesVO.setValues(humiditeVOList);
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


