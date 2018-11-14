package com.terrastation.sha.Controller;

import com.terrastation.sha.Exception.IdNotExistException;
import com.terrastation.sha.Exception.ParameterErrorException;
import com.terrastation.sha.Service.TerraiumGenereService;
import com.terrastation.sha.Service.TerraiumSensorService;
import com.terrastation.sha.Service.TerraiumService;
import com.terrastation.sha.Util.ResultUtil;
import com.terrastation.sha.VO.*;
import com.terrastation.sha.Enums.ResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.terrastation.sha.Entity.Terraium;
import com.terrastation.sha.Exception.TerraiumException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController

public class TerraiumController {
    Logger log = LoggerFactory.getLogger(TerraiumController.class);

    @Autowired
    private TerraiumService terraiumService;
    @Autowired
    private TerraiumGenereService terraiumGenereService;
    @Autowired
    private TerraiumSensorService terraiumServiceSensor;
    /**
     * recuperer tous les parametres de terraium
     * @return
     */
    @RequestMapping(value = "/terraium/getAll", method = RequestMethod.GET)

    public ResultVO<List<Terraium>> findall() {

        return ResultUtil.success(terraiumService.findAll());

    }
    /**
     * ajouter une ligne de parametre au terraium
     * @param terraium
     * @return
     */
    //json forme donnee
    @RequestMapping(value = "/terraium/add", method = RequestMethod.POST)

    public ResultVO<Terraium> add(@RequestBody Terraium terraium) {
        Terraium t = new Terraium();
        t.setHumidite(terraium.getHumidite());
        t.setTemperature(terraium.getTemperature());
        Terraium t_add=terraiumService.save(t);
        return ResultUtil.success(t_add);

    }

//modifier une ligne de parametre au terraium

    @PutMapping(value = "/terraium/update/{id}")
    public ResultVO<Terraium> updateTerraium(@PathVariable(value = "id") int terraiumId,
                                             @RequestParam(value = "temperature", required = false, defaultValue = "0") double temperature,
                                             @RequestParam(value = "humidite", required = false, defaultValue = "0") double humidite) {
        Optional<Terraium> terraiumOriginal = terraiumService.findById(terraiumId);
        Terraium terraiumNew = null;
        if (!terraiumOriginal.isPresent()) {
            throw new TerraiumException(ResultEnum.ID_NOT_EXIST);
        } else {
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
        }

        return ResultUtil.success(terraiumService.save(terraiumNew));
    }

    //supprimer une ligne de parametre au terraium
    @DeleteMapping("/terraium/delete/{id}")
    public ResultVO<String> deleteTerraium(@PathVariable(value = "id") int noteId) {
        Optional<Terraium> terraiumOriginal = terraiumService.findById(noteId);
        Terraium terraium = null;
        if (!terraiumOriginal.isPresent()) {
            throw new IdNotExistException(ResultEnum.ID_NOT_EXIST);
        } else {
            terraium = terraiumOriginal.get();
            terraiumService.delete(terraium);

        } return  ResultUtil.success("vous avez reussi de supprimer : "+noteId);

    }
    //recuperer tous les lignes recentes des parametres
    @RequestMapping(value = "/terraium/getCurrentParametres", method = RequestMethod.GET)
    public ResultVO<List<Terraium> >getCurrentParametres(@RequestParam(value = "quantite", required = false, defaultValue = "6" )int quantite) {

        return ResultUtil.success(terraiumService.getCurrentParametres(quantite));

    }
   @RequestMapping(value = "/terraium/getCurrentParametresVO", method = RequestMethod.GET)

    public ResultVO<TerraiumsSensorVO>getCurrentParametresVO(@RequestParam(value = "quantite", required = false, defaultValue = "6" )int quantite) {
       TerraiumsSensorVO terraiumsGenereVO=new TerraiumsSensorVO();
      List<TerraiumsVO> terraiumsVOList=new ArrayList<TerraiumsVO>();

       TerraiumsVO temperaturesVO= terraiumService.GetCurrentTemperaturesVO(quantite );
       TerraiumsVO humiditesVO= terraiumService.GetCurrentHumiditesVO(quantite);
       terraiumsVOList.add(temperaturesVO);
       terraiumsVOList.add(humiditesVO);
       terraiumsGenereVO.setSensors(terraiumsVOList);

        return ResultUtil.success(terraiumsGenereVO);

    }
    @RequestMapping(value = "/terraium/getCurrentParametresGenereVO", method = RequestMethod.GET)

    public ResultVO<TerraiumsSensorGenereVO> getCurrentParametresGenereVO(int quantite) {
        TerraiumsSensorGenereVO terraiumsGenereVO=new TerraiumsSensorGenereVO();
        List<TerraiumsGenereVO> terraiumsVOList=new ArrayList<TerraiumsGenereVO>();

        TerraiumsGenereVO temperaturesVO= terraiumGenereService.GetCurrentTemperaturesVO(quantite);
        TerraiumsGenereVO humiditesVO= terraiumGenereService.GetCurrentHumiditesVO(quantite);
        terraiumsVOList.add(temperaturesVO);
        terraiumsVOList.add(humiditesVO);
        terraiumsGenereVO.setSensors(terraiumsVOList);

        return ResultUtil.success(terraiumsGenereVO);

    }
    @RequestMapping(value = "/terraium/listSensors", method = RequestMethod.GET)

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
    @RequestMapping(value = "/terraium/listSensors/{id}", method = RequestMethod.GET)

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


