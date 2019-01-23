package com.terrastation.sha.Controller;

import com.terrastation.sha.Entity.Interrupteur;
import com.terrastation.sha.Entity.Lumiere;
import com.terrastation.sha.Entity.Profil;
import com.terrastation.sha.Entity.Terrarium;
import com.terrastation.sha.Exception.IdNotExistException;
import com.terrastation.sha.Exception.ParameterErrorException;
import com.terrastation.sha.Repositary.*;
import com.terrastation.sha.Service.InterrupteurService;
import com.terrastation.sha.Service.TerrariumGenereService;
import com.terrastation.sha.Service.TerrariumSensorService;
import com.terrastation.sha.Service.TerrariumService;
import com.terrastation.sha.Util.ResultUtil;
import com.terrastation.sha.VO.*;
import com.terrastation.sha.Enums.ResultEnum;
import jnr.ffi.annotations.In;
import org.aspectj.weaver.ast.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.terrastation.sha.Exception.TerraiumException;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin
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

    @Autowired
    private TerrariumRepositary terrariumRepositary;

    @Autowired
    private InterrupteurRepository interrupteurRepository;
    @Autowired
    private InterrupteurService interrupteurService;

    @Autowired
    private ChauffageRepository chauffageRepositary;
    @Autowired
    private LumiereRepository lumiereRepositary;

    @Autowired
    private ProfilRepository profilRepository;

    /**
     * recuperer tous les parametres de terraium
     *
     * @return
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)

    public ResultVO<List<Terrarium>> findall() {

        return ResultUtil.success(terrariumService.findAll());

    }

    /**
     * ajouter une ligne de parametre au terraium
     *
     * @param terraium
     * @return
     */
    //json forme donnee
    @RequestMapping(value = "/add", method = RequestMethod.POST)

    public ResultVO<Terrarium> add(@RequestBody Terrarium terraium) {
        Terrarium t = new Terrarium();
        t.setHumidite(terraium.getHumidite());
        t.setTemperature(terraium.getTemperature());
        Terrarium t_add = terrariumService.save(t);
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

        }
        terraiumOriginal.orElseThrow(() -> new IdNotExistException(ResultEnum.ID_NOT_EXIST));

        return ResultUtil.success(terrariumService.save(terraiumNew));
    }

    //supprimer une ligne de parametre au terraium
    @DeleteMapping("/delete/{id}")
    public ResultVO<String> deleteTerraium(@PathVariable(value = "id") int noteId) {
        Optional<Terrarium> terraiumOriginal = terrariumService.findById(noteId);
        Terrarium terraium = null;
        if (terraiumOriginal.isPresent()) {
            terraium = terraiumOriginal.get();
            terrariumService.delete(terraium);
        }
        terraiumOriginal.orElseThrow(() -> new IdNotExistException(ResultEnum.ID_NOT_EXIST));

        return ResultUtil.success("vous avez reussi de supprimer : " + noteId);

    }

    //recuperer tous le plus recente des parametres
    @RequestMapping(value = "/getCurrentParametre", method = RequestMethod.GET)

    public ResultVO<List<TerrariumCurrentVO>> getCurrentParametresGenereVO() {
        List<TerrariumCurrentVO> terraiumsVOList = new ArrayList<TerrariumCurrentVO>();
        Terrarium terrarium = terrariumService.getCurrentParameter();
        TerrariumCurrentVO temperaturesVO = new TerrariumCurrentVO();
        Interrupteur interrupteur = interrupteurService.getControleInterrupteur("chauffage");
        if (interrupteur.isEtat()) {
            temperaturesVO.setIsOn("true");
        } else {
            temperaturesVO.setIsOn("false");
        }
        if (interrupteur.isProg()) {
            temperaturesVO.setIsProg("true");
        } else {

            temperaturesVO.setIsProg("false");
        }
        temperaturesVO.setName("temperature");
        temperaturesVO.setSymbol("°C");
        temperaturesVO.setT(terrarium.getCreateTime());
        temperaturesVO.setY(terrarium.getTemperature());
        temperaturesVO.setId(1);

        TerrariumCurrentVO humiditesVO = new TerrariumCurrentVO();
        humiditesVO.setSymbol("%");
        humiditesVO.setY(terrarium.getHumidite());
        humiditesVO.setT(terrarium.getCreateTime());
        humiditesVO.setIsProg("true");
        humiditesVO.setIsOn("true");
        humiditesVO.setName("humidity");
        humiditesVO.setId(2);

        terraiumsVOList.add(temperaturesVO);
        terraiumsVOList.add(humiditesVO);


        return ResultUtil.success(terraiumsVOList);

    }

    @RequestMapping(value = "/getParametres", method = RequestMethod.GET)
    public ResultVO<TerraiumsSensorGenereVO> getCurrentParametresGenereVO(@RequestParam(value = "quantite", required = false, defaultValue = "6") int quantite) {
        TerraiumsSensorGenereVO terraiumsGenereVO = new TerraiumsSensorGenereVO();
        List<TerrariumsGenereVO> terraiumsVOList = new ArrayList<TerrariumsGenereVO>();

        TerrariumsGenereVO temperaturesVO = terrariumGenereService.GetCurrentTemperaturesVO(quantite);
        TerrariumsGenereVO humiditesVO = terrariumGenereService.GetCurrentHumiditesVO(quantite);
        terraiumsVOList.add(temperaturesVO);
        terraiumsVOList.add(humiditesVO);
        terraiumsGenereVO.setSensors(terraiumsVOList);

        return ResultUtil.success(terraiumsGenereVO);

    }

    @RequestMapping(value = "/getParametres/{id}", method = RequestMethod.GET)
    public ResultVO<SensorVO> getCurrentParametresGenereVOById(@PathVariable(value = "id") int index, @RequestParam(value = "quantite", required = false, defaultValue = "6") int quantite) {
        SensorVO sensorVO = new SensorVO();
        sensorVO = terraiumServiceSensor.getSensorByIdVO(index, quantite);
        return ResultUtil.success(sensorVO);

    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //recuperer tous les lignes recentes des parametres
    @RequestMapping(value = "/getCurrentParametres", method = RequestMethod.GET)
    public ResultVO<List<Terrarium>> getCurrentParametres(@RequestParam(value = "quantite", required = false, defaultValue = "6") int quantite) {

        return ResultUtil.success(terrariumService.getCurrentParameters(quantite));

    }

    @RequestMapping(value = "/getCurrentParametresVO", method = RequestMethod.GET)

    public ResultVO<TerrariumsSensorVO> getCurrentParametresVO(@RequestParam(value = "quantite", required = false, defaultValue = "6") int quantite) {
        TerrariumsSensorVO terraiumsGenereVO = new TerrariumsSensorVO();
        List<TerrariumsVO> terrariumsVOList = new ArrayList<TerrariumsVO>();

        TerrariumsVO temperaturesVO = terrariumService.GetCurrentTemperaturesVO(quantite);
        TerrariumsVO humiditesVO = terrariumService.GetCurrentHumiditesVO(quantite);
        terrariumsVOList.add(temperaturesVO);
        terrariumsVOList.add(humiditesVO);
        terraiumsGenereVO.setSensors(terrariumsVOList);

        return ResultUtil.success(terraiumsGenereVO);

    }


    @RequestMapping(value = "/listSensors", method = RequestMethod.GET)

    public ResultVO<SensorsVO> getListSensors() {

        SensorsVO sensorsVO = new SensorsVO();
        List<SensorAffichageVO> sensorVOList = new ArrayList<SensorAffichageVO>();
        SensorAffichageVO temperatureSensor = new SensorAffichageVO(1, "temperature", "°C");
        SensorAffichageVO humiditeSensor = new SensorAffichageVO(2, "humidity", "%");
        sensorVOList.add(temperatureSensor);
        sensorVOList.add(humiditeSensor);
        sensorsVO.setSensors(sensorVOList);
        return ResultUtil.success(sensorsVO);

    }

    @RequestMapping(value = "/listSensors/{id}", method = RequestMethod.GET)

    public ResultVO<SensorVO> getListSensors(@PathVariable(value = "id") int index, @RequestParam(value = "quantite", required = false, defaultValue = "6") int quantite) {
        SensorVO sensorVO = new SensorVO();
        sensorVO = terraiumServiceSensor.getSensorByIdVO(index, quantite);
        return ResultUtil.success(sensorVO);

    }

    @RequestMapping(value = "/getDonneeUneSemaine", method = RequestMethod.GET)

    public List<Terrarium> getDonneeUneSemaine() {
        List<Terrarium> terrariums = terrariumGenereService.getDonneeUneSemaine();
        return terrariums;
    }

    @RequestMapping(value = "/getParametresUneSemaine", method = RequestMethod.GET)

    public ResultVO<TerraiumsSensorGenereVO> getCurrentParametresUneSemaine() {
        TerraiumsSensorGenereVO terraiumsGenereVO = new TerraiumsSensorGenereVO();
        List<TerrariumsGenereVO> terraiumsVOList = new ArrayList<TerrariumsGenereVO>();

        TerrariumsGenereVO temperaturesVO = terrariumGenereService.GetUneSemaineTemperaturesVO();
        TerrariumsGenereVO humiditesVO = terrariumGenereService.GetUneSemaineHumiditesVO();
        terraiumsVOList.add(temperaturesVO);
        terraiumsVOList.add(humiditesVO);
        terraiumsGenereVO.setSensors(terraiumsVOList);

        return ResultUtil.success(terraiumsGenereVO);

    }

    @RequestMapping(value = "/getParametresUneSemaine/{id}", method = RequestMethod.GET)

    public ResultVO<TerrariumsGenereVO> getParametresUneSemaineById(@PathVariable(value = "id") int index) {
        TerrariumsGenereVO terraiumsGenereVO = new TerrariumsGenereVO();
        if (index == 1) {
            terraiumsGenereVO = terrariumGenereService.GetUneSemaineTemperaturesVO();
        } else if (index == 2) {
            terraiumsGenereVO = terrariumGenereService.GetUneSemaineHumiditesVO();
        }
        return ResultUtil.success(terraiumsGenereVO);

    }

    @RequestMapping(value = "/getTemperaturesUneSemaine", method = RequestMethod.GET)

    public ResultVO<TerrariumsGenereVO> getCurrentTemperatureUneSemaine() {
        TerrariumsGenereVO terraiumsGenereVO = terrariumGenereService.GetUneSemaineTemperaturesVO();

        return ResultUtil.success(terraiumsGenereVO);

    }

    @RequestMapping(value = "/getHumiditeUneSemaine", method = RequestMethod.GET)

    public ResultVO<TerrariumsGenereVO> getCurrentHumiditeUneSemaine() {
        TerrariumsGenereVO terraiumsGenereVO = terrariumGenereService.GetUneSemaineHumiditesVO();

        return ResultUtil.success(terraiumsGenereVO);

    }

    @RequestMapping(value = "/getDonneeUnMois", method = RequestMethod.GET)

    public List<Terrarium> getDonneeUnMois() {
        List<Terrarium> terrariums = terrariumGenereService.getDonneeUnMois();
        return terrariums;
    }

    @RequestMapping(value = "/getParametresUnMois", method = RequestMethod.GET)

    public ResultVO<TerraiumsSensorGenereVO> getCurrentParametresUnMois() {
        TerraiumsSensorGenereVO terraiumsGenereVO = new TerraiumsSensorGenereVO();
        List<TerrariumsGenereVO> terraiumsVOList = new ArrayList<TerrariumsGenereVO>();

        TerrariumsGenereVO temperaturesVO = terrariumGenereService.GetUnMoisTemperaturesVO();
        TerrariumsGenereVO humiditesVO = terrariumGenereService.GetUnMoisHumiditesVO();
        terraiumsVOList.add(temperaturesVO);
        terraiumsVOList.add(humiditesVO);
        terraiumsGenereVO.setSensors(terraiumsVOList);

        return ResultUtil.success(terraiumsGenereVO);

    }

    @RequestMapping(value = "/getTemperaturesUnMois", method = RequestMethod.GET)

    public ResultVO<TerrariumsGenereVO> getCurrentTemperatureUnMois() {
        TerrariumsGenereVO terraiumsGenereVO = terrariumGenereService.GetUnMoisTemperaturesVO();

        return ResultUtil.success(terraiumsGenereVO);

    }

    @RequestMapping(value = "/getHumiditeUnMois", method = RequestMethod.GET)

    public ResultVO<TerrariumsGenereVO> getCurrentHumiditeUnMois() {
        TerrariumsGenereVO terraiumsGenereVO = terrariumGenereService.GetUnMoisHumiditesVO();

        return ResultUtil.success(terraiumsGenereVO);

    }

    @RequestMapping(value = "/getParametresUnMois/{id}", method = RequestMethod.GET)

    public ResultVO<TerrariumsGenereVO> getParametresUnMoisById(@PathVariable(value = "id") int index) {
        TerrariumsGenereVO terraiumsGenereVO = new TerrariumsGenereVO();
        if (index == 1) {
            terraiumsGenereVO = terrariumGenereService.GetUnMoisTemperaturesVO();
        } else if (index == 2) {
            terraiumsGenereVO = terrariumGenereService.GetUnMoisHumiditesVO();
        }
        return ResultUtil.success(terraiumsGenereVO);

    }

    @RequestMapping(value = "/getMode", method = RequestMethod.GET)

    public ResultVO<List<Interrupteur>> getModes() {
        Interrupteur chauffage = interrupteurService.getControleInterrupteur("chauffage");
        Interrupteur lumiere = interrupteurService.getControleInterrupteur("lumiere");
        List<Interrupteur> interrupteurs = new ArrayList<Interrupteur>();
        interrupteurs.add(chauffage);
        interrupteurs.add(lumiere);
        return ResultUtil.success(interrupteurs);

    }

    @RequestMapping(value = "/getMode/{id}", method = RequestMethod.GET)

    public ResultVO<Interrupteur> getModesById(@PathVariable(value = "id") int index) {
        Interrupteur chauffage = interrupteurService.getControleInterrupteur("chauffage");
        Interrupteur lumiere = interrupteurService.getControleInterrupteur("lumiere");
        if (index == chauffage.getId()) {
            return ResultUtil.success(chauffage);

        } else if (index == lumiere.getId()) {
            return ResultUtil.success(lumiere);
        } else {
            return ResultUtil.success(null);
        }
    }


    /**
     * 文件上传具体实现方法;
     *
     * @param file
     * @return
     */
    @RequestMapping("/upload")
    @ResponseBody
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                BufferedOutputStream out = new BufferedOutputStream(

                new FileOutputStream(new File(
                               "../static/profil.jpg")));
                System.out.println(file.getName());
                out.write(file.getBytes());
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return "echoueur de upload," + e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                return "echoueur de upload," + e.getMessage();
            }
            log.info("upload successfully");
            return "upload successfully";

        } else {
            log.info("echoueur de upload,le fichier est vide");
            return "echoueur de upload,le fichier est vide.";

        }
    }

    @RequestMapping(value = "/getImage", method = RequestMethod.GET,produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[]  chargeImage() throws IOException {
        File file = new File("../static/profil.jpg");
        if (file.exists()) {
            log.info("vous avez  donne le photo");
            FileInputStream inputStream = new FileInputStream(file);

            byte[] bytes = new byte[inputStream.available()];

            inputStream.read(bytes, 0, inputStream.available());

            return bytes;

           // return "http://127.0.0.1:8080/profil.jpg";
        } else {
            log.info("vous avez pas encore donner le photo");
            return null;
        }




//        if (file.exists()) {
//            return "http://127.0.0.1:8080/profil.jpg";
//        } else {
//            return "vous avez pas encore donner le photo";
//        }
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


