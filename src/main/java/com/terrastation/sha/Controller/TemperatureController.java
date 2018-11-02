package com.terrastation.sha.Controller;

import com.terrastation.sha.Entity.Terraium;
import com.terrastation.sha.Util.ResultUtil;
import com.terrastation.sha.VO.ResultVO;
import com.terrastation.sha.Exception.TerraiumException;
import com.terrastation.sha.Repositary.TerraiumRepositary;
import com.terrastation.sha.VO.TerraiumVO;
import com.terrastation.sha.VO.TerraiumsVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController

public class TemperatureController {
    Logger log = LoggerFactory.getLogger(TemperatureController.class);


    @Autowired
    private TerraiumRepositary terraiumRepositary;



//recuperer les temperatures recentes, maximal et minimal
    @RequestMapping(value = "/terraium/temperature/getCurrentTemperaturesVO", method = RequestMethod.GET)
    public ResultVO<TerraiumsVO> getCurrentTemperaturesVO() {
        List<Terraium> terraiumList = terraiumRepositary.findCurrentTemperatures(6);
        Collections.reverse(terraiumList);
        List<TerraiumVO> terraiumListVO = new ArrayList<TerraiumVO>();
        for (Terraium t : terraiumList) {
            TerraiumVO temperatureVO = new TerraiumVO();
            temperatureVO.setId(t.getId());
            temperatureVO.setValeur(t.getTemperature());
            temperatureVO.setCreateTime(t.getCreateTime());
            temperatureVO.setUpdateTime(t.getUpdateTime());
            terraiumListVO.add(temperatureVO);
            temperatureVO.setSymbol("%");
            temperatureVO.setType("Temperature");

        }
        Terraium temp_max = terraiumRepositary.findMaxTemperatures(6);
        TerraiumVO tempVO_max = new TerraiumVO();
        tempVO_max.setId(temp_max.getId());
        tempVO_max.setUpdateTime(temp_max.getUpdateTime());
        tempVO_max.setCreateTime(temp_max.getCreateTime());
        tempVO_max.setValeur(temp_max.getTemperature());
        tempVO_max.setSymbol("%");
        tempVO_max.setType("Temperature");

        Terraium temp_min = terraiumRepositary.findMinTemperatures(6);
        TerraiumVO tempVO_min = new TerraiumVO();
        tempVO_min.setId(temp_min.getId());
        tempVO_min.setUpdateTime(temp_min.getUpdateTime());
        tempVO_min.setCreateTime(temp_min.getCreateTime());
        tempVO_min.setValeur(temp_min.getTemperature());
        tempVO_min.setSymbol("%");
        tempVO_min.setType("Temperature");

        TerraiumsVO temperaturesVO=new TerraiumsVO();
        temperaturesVO.setMax(tempVO_max);
        temperaturesVO.setMin(tempVO_min);
        temperaturesVO.setTerraiumsVO(terraiumListVO);
        return ResultUtil.success(temperaturesVO);

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


