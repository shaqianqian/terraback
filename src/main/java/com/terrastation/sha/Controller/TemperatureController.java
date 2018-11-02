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
            temperatureVO.setValue(t.getTemperature());
            temperatureVO.setTime(t.getCreateTime());
            terraiumListVO.add(temperatureVO);

        }
        Terraium temp_max = terraiumRepositary.findMaxTemperatures(6);
        TerraiumVO tempVO_max = new TerraiumVO();
        tempVO_max.setTime(temp_max.getCreateTime());
        tempVO_max.setValue(temp_max.getTemperature());



        Terraium temp_min = terraiumRepositary.findMinTemperatures(6);
        TerraiumVO tempVO_min = new TerraiumVO();
        tempVO_min.setTime(temp_min.getCreateTime());
        tempVO_min.setValue(temp_min.getTemperature());



        TerraiumsVO temperaturesVO=new TerraiumsVO();
        temperaturesVO.setSymbol("°C");
        temperaturesVO.setType("Temperature");
        temperaturesVO.setMax(tempVO_max);
        temperaturesVO.setMin(tempVO_min);
        temperaturesVO.setValues(terraiumListVO);

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


