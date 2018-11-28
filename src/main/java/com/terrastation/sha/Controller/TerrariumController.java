package com.terrastation.sha.Controller;

import com.terrastation.sha.Entity.Terrarium;
import com.terrastation.sha.Exception.IdNotExistException;
import com.terrastation.sha.Exception.ParameterErrorException;
import com.terrastation.sha.Service.TerrariumGenereService;
import com.terrastation.sha.Service.TerrariumSensorService;
import com.terrastation.sha.Service.TerrariumService;
import com.terrastation.sha.Util.ResultUtil;
import com.terrastation.sha.VO.*;
import com.terrastation.sha.Enums.ResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.terrastation.sha.Exception.TerraiumException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/terrarium")
public class TerrariumController {
    Logger log = LoggerFactory.getLogger(TerrariumController.class);

    @Autowired
    private TerrariumService terrariumService;
    @Autowired
    private TerrariumGenereService terrariumGenereService;
    @Autowired
    private TerrariumSensorService terraiumServiceSensor;
    /**
     * recuperer tous les parametres de terraium
     * @return
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)

    public ResultVO<List<Terrarium>> findall() {

        return ResultUtil.success(terrariumService.findAll());

    }
    /**
     * ajouter une ligne de parametre au terraium
     * @param terraium
     * @return
     */
    //json forme donnee
    @RequestMapping(value = "/add", method = RequestMethod.POST)

    public ResultVO<Terrarium> add(@RequestBody Terrarium terraium) {
        Terrarium t = new Terrarium();
        t.setHumidite(terraium.getHumidite());
        t.setTemperature(terraium.getTemperature());
        Terrarium t_add= terrariumService.save(t);
        return ResultUtil.success(t_add);

    }

//modifier une ligne de parametre au terraium

    @PutMapping(value = "/update/{id}")
    public ResultVO<Terrarium> updateTerraium(@PathVariable(value = "id") int terraiumId,
                                             @RequestParam(value = "temperature", required = false, defaultValue = "0") double temperature,
                                             @RequestParam(value = "humidite", required = false, defaultValue = "0") double humidite) {
        Optional<Terrarium> terraiumOriginal = terrariumService.findById(terraiumId);
        Terrarium terraiumNew = null;
        if (terraiumOriginal.isPresent()) {
            terraiumNew = terraiumOriginal.get();

            if (temperature != 0 && humidite == 0) {
                terraiumNew.setTemperature(temperature);
            } else if (temperature == 0 && humidite != 0) {
                terraiumNew.setHumidite(humidite);
            } else if (temperature != 0 && humidite != 0) {
                terraiumNew.setTemperature(temperature);
                terraiumNew.setHumidite(humidite);
            } else {
                throw new ParameterErrorException(ResultEnum.PARAM_ERROR);
            }

        }   terraiumOriginal.orElseThrow(()->new IdNotExistException(ResultEnum.ID_NOT_EXIST));

        return ResultUtil.success(terrariumService.save(terraiumNew));
    }

    //supprimer une ligne de parametre au terraium
    @DeleteMapping("/delete/{id}")
    public ResultVO<String> deleteTerraium(@PathVariable(value = "id") int noteId) {
        Optional<Terrarium> terraiumOriginal = terrariumService.findById(noteId);
        Terrarium terraium = null;
        if(terraiumOriginal.isPresent()){
            terraium = terraiumOriginal.get();
            terrariumService.delete(terraium);
        }
       terraiumOriginal.orElseThrow(()->new IdNotExistException(ResultEnum.ID_NOT_EXIST));

        return  ResultUtil.success("vous avez reussi de supprimer : "+noteId);

    }
    //recuperer tous les lignes recentes des parametres
    @RequestMapping(value = "/getCurrentParametres", method = RequestMethod.GET)
    public ResultVO<List<Terrarium> >getCurrentParametres(@RequestParam(value = "quantite", required = false, defaultValue = "6" )int quantite) {

        return ResultUtil.success(terrariumService.getCurrentParameters(quantite));

    }
   @RequestMapping(value = "/getCurrentParametresVO", method = RequestMethod.GET)

    public ResultVO<TerrariumsSensorVO>getCurrentParametresVO(@RequestParam(value = "quantite", required = false, defaultValue = "6" )int quantite) {
       TerrariumsSensorVO terraiumsGenereVO=new TerrariumsSensorVO();
      List<TerrariumsVO> terrariumsVOList =new ArrayList<TerrariumsVO>();

       TerrariumsVO temperaturesVO= terrariumService.GetCurrentTemperaturesVO(quantite );
       TerrariumsVO humiditesVO= terrariumService.GetCurrentHumiditesVO(quantite);
       terrariumsVOList.add(temperaturesVO);
       terrariumsVOList.add(humiditesVO);
       terraiumsGenereVO.setSensors(terrariumsVOList);

        return ResultUtil.success(terraiumsGenereVO);

    }
    @RequestMapping(value = "/getCurrentParametresGenereVO", method = RequestMethod.GET)

    public ResultVO<TerraiumsSensorGenereVO> getCurrentParametresGenereVO(@RequestParam(value = "quantite", required = false, defaultValue = "6" )int quantite) {
        TerraiumsSensorGenereVO terraiumsGenereVO=new TerraiumsSensorGenereVO();
        List<TerrariumsGenereVO> terraiumsVOList=new ArrayList<TerrariumsGenereVO>();

        TerrariumsGenereVO temperaturesVO= terrariumGenereService.GetCurrentTemperaturesVO(quantite);
        TerrariumsGenereVO humiditesVO= terrariumGenereService.GetCurrentHumiditesVO(quantite);
        terraiumsVOList.add(temperaturesVO);
        terraiumsVOList.add(humiditesVO);
        terraiumsGenereVO.setSensors(terraiumsVOList);

        return ResultUtil.success(terraiumsGenereVO);

    }
    @RequestMapping(value = "/listSensors", method = RequestMethod.GET)

    public ResultVO<SensorsVO> getListSensors() {

        SensorsVO sensorsVO=new SensorsVO();
        List<SensorAffichageVO> sensorVOList=new ArrayList<SensorAffichageVO>();
        SensorAffichageVO temperatureSensor=new SensorAffichageVO(1,"temperature","°C");
        SensorAffichageVO  humiditeSensor=new SensorAffichageVO (2,"humidity","%");
        sensorVOList.add(temperatureSensor);
        sensorVOList.add(humiditeSensor);
        sensorsVO.setSensors(sensorVOList);
        return ResultUtil.success(sensorsVO);

    }
    @RequestMapping(value = "/listSensors/{id}", method = RequestMethod.GET)

    public ResultVO<SensorVO> getListSensors(@PathVariable(value = "id") int index,@RequestParam(value = "quantite", required = false, defaultValue = "6" )int quantite) {
        SensorVO sensorVO=new SensorVO();
        sensorVO= terraiumServiceSensor.getSensorByIdVO(index,quantite);
        return ResultUtil.success(sensorVO);

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


