package com.terrastation.sha.Controller;

import com.terrastation.sha.Util.ResultUtil;
import com.terrastation.sha.VO.ResultVO;
import com.terrastation.sha.Enums.ResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.terrastation.sha.Repositary.*;
import com.terrastation.sha.Entity.Terraium;
import com.terrastation.sha.Exception.TerraiumException;

import java.util.List;
import java.util.Optional;

@RestController

public class TerraiumController {
    Logger log = LoggerFactory.getLogger(TerraiumController.class);


    @Autowired
    private TerraiumRepositary terraiumRepositary;

    /**
     * recuperer tous les parametres de terraium
     * @return
     */
    @RequestMapping(value = "/terraium/getAll", method = RequestMethod.GET)

    public ResultVO<List<Terraium>> findall() {

        return ResultUtil.success(terraiumRepositary.findAll());

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
        Terraium t_add=terraiumRepositary.save(t);
        return ResultUtil.success(t_add);

    }

//modifier une ligne de parametre au terraium
    @PutMapping(value = "/terraium/update/{id}")
    public  ResultVO<Terraium> updateTerraium(@PathVariable(value = "id") int terraiumId,
                               @RequestBody Terraium terraiumDetails) {
        Optional<Terraium> terraiumOriginal = terraiumRepositary.findById(terraiumId);
        Terraium terraiumNew = null;
        if (!terraiumOriginal.isPresent()) {
            throw new TerraiumException(ResultEnum.ID_NOT_EXIST);
        } else {
            terraiumNew = terraiumOriginal.get();
            terraiumNew.setTemperature(terraiumDetails.getTemperature());
            terraiumNew.setHumidite(terraiumDetails.getHumidite());
        }
        return  ResultUtil.success(terraiumRepositary.save(terraiumNew));
    }
    //supprimer une ligne de parametre au terraium
    @DeleteMapping("/terraium/delete/{id}")
    public  ResultVO<String> deleteTerraium(@PathVariable(value = "id") int noteId) {
        Optional<Terraium> terraiumOriginal = terraiumRepositary.findById(noteId);
        Terraium terraium = null;
        if (!terraiumOriginal.isPresent()) {
            throw new TerraiumException(ResultEnum.ID_NOT_EXIST);
        } else {
            terraium = terraiumOriginal.get();
            terraiumRepositary.delete(terraium);

        } return  ResultUtil.success("vous avez reussi de supprimer : "+noteId);

    }

    //recuperer tous les lignes recentes des parametres
    @RequestMapping(value = "/terraium/getCurrentParametres", method = RequestMethod.GET)
    public ResultVO<List<Terraium> >getCurrentParametres() {


        return ResultUtil.success(terraiumRepositary.findCurrentTemperatures(6));

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


