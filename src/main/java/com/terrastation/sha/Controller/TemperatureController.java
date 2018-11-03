package com.terrastation.sha.Controller;

import com.terrastation.sha.Entity.Terraium;
import com.terrastation.sha.Service.TerraiumService;
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
    @Autowired
    private TerraiumService terraiumService;



//recuperer les temperatures recentes, maximal et minimal
    @RequestMapping(value = "/terraium/temperature/getCurrentTemperaturesVO", method = RequestMethod.GET)
    public ResultVO<TerraiumsVO> getCurrentTemperaturesVO() {


        TerraiumsVO temperaturesVO=terraiumService.GetCurrentTemperaturesVO();


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


