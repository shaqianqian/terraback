package com.terrastation.sha.Controller;


import com.terrastation.sha.Entity.Chauffage;
import com.terrastation.sha.Repositary.ChauffageRepository;
import com.terrastation.sha.Util.ResultUtil;
import com.terrastation.sha.VO.ResultVO;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

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
        Chauffage rep=new Chauffage();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date timeDebut;
        Date timeFin;
        try {
            timeDebut=format.parse(dateDebut);
            rep.setDateDebut(timeDebut);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            timeDebut=format.parse(dateFin);
            rep.setDateFin(timeDebut);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        rep.setMin(min);
        rep.setMax(max);
        rep.setEtat(etat);

        return   ResultUtil.success(chauffageRepository.save(rep));

    }



    @PutMapping("/prereglages/{id}")
    public Chauffage updateNote(@PathVariable(value = "id") int chauffageId,
                                @Valid @RequestBody Chauffage chauffageDetails) {
        Optional<Chauffage> chauf = chauffageRepository.findById(chauffageId);
        Chauffage chauffage1=null;
        if(chauf.isPresent()) {
            chauffage1=chauf.get();
            chauffage1.setDateDebut(chauffageDetails.getDateDebut());
            chauffage1.setDateFin(chauffageDetails.getDateFin());
            chauffage1.setMin(chauffageDetails.getMin());
            chauffage1.setMax(chauffageDetails.getMax());
            chauffage1.setEtat(chauffageDetails.isEtat());

        }
        Chauffage chauffagePrereglages = chauffageRepository.save(chauffage1);
        return chauffagePrereglages;
    }




}