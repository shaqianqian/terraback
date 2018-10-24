package com.terrastation.sha.Controller;

import com.terrastation.sha.Util.ResultUtil;
import com.terrastation.sha.VO.ResultVO;
import com.terrastation.sha.enums.ResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.terrastation.sha.repositary.*;
import com.terrastation.sha.domain.terraium;
import com.terrastation.sha.exception.terraiumException;

import java.util.List;
import java.util.Optional;

@RestController

public class terraiumController {
    Logger log = LoggerFactory.getLogger(terraiumController.class);


    @Autowired
    private terraiumRepositary terraiumRepositary;


    @RequestMapping(value = "getAll", method = RequestMethod.GET)

    public ResultVO<List<terraium>> findall() {

        return ResultUtil.success(terraiumRepositary.findAll());

    }

    //json forme donnee
    @RequestMapping(value = "add", method = RequestMethod.POST)

    public ResultVO<terraium> add(@RequestBody terraium terraium) {
        terraium t = new terraium();
        t.setHumite(terraium.getHumite());
        t.setTemperature(terraium.getTemperature());
        return ResultUtil.success(terraiumRepositary.save(t));

    }


    @PutMapping(value = "/terraium/{id}")
    public  ResultVO<terraium> updateNote(@PathVariable(value = "id") int terraiumId,
                               @RequestBody terraium terraiumDetails) {
        Optional<terraium> terraiumOriginal = terraiumRepositary.findById(terraiumId);
        terraium terraiumNew = null;
        if (!terraiumOriginal.isPresent()) {
            throw new terraiumException(ResultEnum.ID_NOT_EXIST);
        } else {
            terraiumNew = terraiumOriginal.get();
            terraiumNew.setTemperature(terraiumDetails.getTemperature());
            terraiumNew.setHumite(terraiumDetails.getHumite());
        }
        return  ResultUtil.success(terraiumRepositary.save(terraiumNew));
    }

    @DeleteMapping("/terraium/{id}")
    public  ResultVO<String> deleteReptile(@PathVariable(value = "id") int noteId) {
        Optional<terraium> terraiumOriginal = terraiumRepositary.findById(noteId);
        terraium terraium = null;
        if (!terraiumOriginal.isPresent()) {
            throw new terraiumException(ResultEnum.ID_NOT_EXIST);
        } else {
            terraium = terraiumOriginal.get();
            terraiumRepositary.delete(terraium);

        } return  ResultUtil.success("vous avez reussi de supprimer : "+noteId);

    }


    @ControllerAdvice
    public class ExceptionHandle {
        @ResponseBody
        @ExceptionHandler(value = Exception.class)
        public Object handle(Exception e) {
            if (e instanceof terraiumException) {
                terraiumException te = (terraiumException) e;
                return ResultUtil.error(te.getCode(), te.getMessage());
            } else {
                return ResultUtil.error(-1, "unknown error");
            }
        }


    }
}


