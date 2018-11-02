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
    public ResultVO<HumiditesVO> getCurrentHumiditesVO() {
        List<Terraium> terraiumList = terraiumRepositary.findCurrentTemperatures(6);
        Collections.reverse(terraiumList);
        List<HumiditeVO> humiditeVOList = new ArrayList<HumiditeVO>();
        for (Terraium t : terraiumList) {
            HumiditeVO humiditeVO = new HumiditeVO();
            humiditeVO.setId(t.getId());
            humiditeVO.setHumidite(t.getHumidite());
            humiditeVO.setCreateTime(t.getCreateTime());
            humiditeVO.setUpdateTime(t.getUpdateTime());
            humiditeVOList.add(humiditeVO);

        }
        Terraium hum_max = terraiumRepositary.findMaxHumidites(6);
        HumiditeVO humiditeVO_max = new HumiditeVO();
        humiditeVO_max.setId(hum_max.getId());
        humiditeVO_max.setUpdateTime(hum_max.getUpdateTime());
        humiditeVO_max.setCreateTime(hum_max.getCreateTime());
        humiditeVO_max.setHumidite(hum_max.getHumidite());

        Terraium hum__min = terraiumRepositary.findMinHumidites(6);
        HumiditeVO humiditeVO_min = new HumiditeVO();
        humiditeVO_min.setId(hum__min.getId());
        humiditeVO_min.setUpdateTime(hum__min.getUpdateTime());
        humiditeVO_min.setCreateTime(hum__min.getCreateTime());
        humiditeVO_min.setHumidite(hum__min.getHumidite());

        HumiditesVO humiditesVO=new HumiditesVO();
        humiditesVO.setMax_hum(humiditeVO_max);
        humiditesVO.setMin_hum(humiditeVO_min);
        humiditesVO.setHumiditesVO(humiditeVOList);
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


