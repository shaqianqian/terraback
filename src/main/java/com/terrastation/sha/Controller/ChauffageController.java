package com.terrastation.sha.Controller;


import com.terrastation.sha.Entity.Chauffage;
import com.terrastation.sha.Enums.ResultEnum;
import com.terrastation.sha.Exception.IdNotExistException;
import com.terrastation.sha.Repositary.ChauffageRepository;
import com.terrastation.sha.Util.ResultUtil;
import com.terrastation.sha.VO.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@RestController
public class ChauffageController {

    @Autowired
    private ChauffageRepository chauffageRepository;


    @RequestMapping(value = "/chauffage/getAll", method = RequestMethod.GET)

    public  ResultVO  findall() {
        return ResultUtil.success(chauffageRepository.findAll());

    }

    @RequestMapping(value = "/chauffage/add", method = RequestMethod.POST)

    public ResultVO add(@RequestParam("dateDebut")String dateDebut, @RequestParam("dateFin") String dateFin, @RequestParam("min") double min , @RequestParam("max") double max, @RequestParam("etat")boolean etat) {
        Chauffage rep = new Chauffage();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date timeDebut = null;
        Date timeFin = null;
        try {
            timeDebut = format.parse(dateDebut);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            timeFin = format.parse(dateFin);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (timeDebut.after(timeFin)||timeDebut.equals(timeFin)) {

            throw new IdNotExistException(ResultEnum.Time_Ordre);
        } else {
            rep.setDateDebut(timeDebut);
            rep.setDateFin(timeFin);
            rep.setMin(min);
            rep.setMax(max);
            rep.setEtat(etat);

//        }

            return ResultUtil.success(chauffageRepository.save(rep));

        }



    }


}