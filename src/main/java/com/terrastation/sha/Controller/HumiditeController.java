package com.terrastation.sha.Controller;

import com.terrastation.sha.Exception.TerraiumException;
import com.terrastation.sha.Service.Impl.TerrariumSensorServiceImpl;
import com.terrastation.sha.Util.ResultUtil;
import com.terrastation.sha.VO.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class HumiditeController {
    Logger log = LoggerFactory.getLogger(HumiditeController.class);


    @Autowired
    private TerrariumSensorServiceImpl terraiumServiceSensor;

    //recuperer les humidites recentes, maximal et minimal
    @RequestMapping(value = "/terrarium/humidite/getCurrentHumiditesVO", method = RequestMethod.GET)
    public ResultVO<TerrariumsVO> getCurrentHumiditeVO(@RequestParam(value = "quantite", required = false, defaultValue = "6") int quantite) {

        SensorVO humidite_sensor = new SensorVO();
        humidite_sensor = terraiumServiceSensor.getCurrentHumiditeVO(quantite);

        return ResultUtil.success(humidite_sensor);

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


