package com.terrastation.sha.Controller;

import com.terrastation.sha.Entity.Terraium;
import com.terrastation.sha.Util.ResultUtil;
import com.terrastation.sha.VO.ResultVO;
import com.terrastation.sha.VO.TemperatureVO;
import com.terrastation.sha.Exception.TerraiumException;
import com.terrastation.sha.Repositary.TerraiumRepositary;
import com.terrastation.sha.VO.TemperaturesVO;
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


    @RequestMapping(value = "getCurrentTemperature", method = RequestMethod.GET)

    public ResultVO<Terraium> getCurrentTemperature() {

        return ResultUtil.success(terraiumRepositary.findCurrentTemperature());

    }

    @RequestMapping(value = "getCurrentTemperatures", method = RequestMethod.GET)
    public ResultVO<TemperaturesVO> getCurrentTemperatures() {
        List<Terraium> terraiumList = terraiumRepositary.findCurrentTemperatures(6);
        Collections.reverse(terraiumList);
        List<TemperatureVO> terraiumListVO = new ArrayList<TemperatureVO>();
        for (Terraium t : terraiumList) {
            TemperatureVO temperatureVO = new TemperatureVO();
            temperatureVO.setId(t.getId());
            temperatureVO.setTemperature(t.getTemperature());
            temperatureVO.setCreateTime(t.getCreateTime());
            temperatureVO.setUpdateTime(t.getUpdateTime());
            terraiumListVO.add(temperatureVO);

        }
        Terraium temp_max = terraiumRepositary.findMaxTemperatures(6);
        TemperatureVO tempVO_max = new TemperatureVO();
        tempVO_max.setId(temp_max.getId());
        tempVO_max.setUpdateTime(temp_max.getUpdateTime());
        tempVO_max.setCreateTime(temp_max.getCreateTime());
        tempVO_max.setTemperature(temp_max.getTemperature());

        Terraium temp_min = terraiumRepositary.findMinTemperatures(6);
        TemperatureVO tempVO_min = new TemperatureVO();
        tempVO_min.setId(temp_max.getId());
        tempVO_min.setUpdateTime(temp_min.getUpdateTime());
        tempVO_min.setCreateTime(temp_min.getCreateTime());
        tempVO_min.setTemperature(temp_min.getTemperature());

        TemperaturesVO temperaturesVO=new TemperaturesVO();
        temperaturesVO.setMax_temp(tempVO_max);
        temperaturesVO.setMin_temp(tempVO_min);
        temperaturesVO.setTemperaturesVO(terraiumListVO);
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


