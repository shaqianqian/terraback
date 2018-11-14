package com.terrastation.sha.Controller;

import com.terrastation.sha.Service.Impl.TerraiumServiceImpl;
import com.terrastation.sha.Service.TerraiumSensorService;
import com.terrastation.sha.Util.ResultUtil;
import com.terrastation.sha.VO.*;
import com.terrastation.sha.Exception.TerraiumException;
import com.terrastation.sha.Repositary.TerraiumRepositary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController

public class TemperatureController {
    Logger log = LoggerFactory.getLogger(TemperatureController.class);


    @Autowired
    private TerraiumSensorService terraiumSensorService;


//recuperer les temperatures recentes, maximal et minimal
    @RequestMapping(value = "/terraium/temperature/getCurrentTemperaturesVO", method = RequestMethod.GET)
    public ResultVO<TerraiumsVO> getCurrentTemperaturesVO(@RequestParam(value = "quantite", required = false, defaultValue = "6" )int quantite) {



        SensorVO temperature_sensor=new SensorVO();

        temperature_sensor= terraiumSensorService.getCurrentTemperaturesVO(quantite);


        return ResultUtil.success(temperature_sensor);

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


