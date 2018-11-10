package com.terrastation.sha.Controller;

import com.terrastation.sha.Entity.Chauffage;
import com.terrastation.sha.Entity.Piece;
import com.terrastation.sha.Entity.Terraium;
import com.terrastation.sha.Repositary.ChauffageRepository;
import com.terrastation.sha.Repositary.PieceRepository;
import com.terrastation.sha.Repositary.TerraiumRepositary;
import com.terrastation.sha.Util.ResultUtil;
import com.terrastation.sha.VO.ResultVO;
import com.terrastation.sha.VO.TerraiumsVO;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


@RestController
public class PieceController {
    org.slf4j.Logger log = LoggerFactory.getLogger(TerraiumController.class);
    @Autowired
    private ChauffageRepository chauffageRepository;

    @Autowired
    private PieceRepository pieceRepository;
    @Autowired
    private TerraiumRepositary temrraiumRepository;


    //get l'etat de chauffage
    @RequestMapping(value = "/terraium/chauffage/getEtatChauffage", method = RequestMethod.GET)


    public ResultVO<Piece> getEtatChauffageVO() {
        Piece piece;
        if (pieceRepository.findAll().size() == 0) {
            Piece newPiece = new Piece();
            newPiece.setEtat(false);
            newPiece.setType("chauffage");
            piece = pieceRepository.save(newPiece);

        } else {
            piece = pieceRepository.findAll().get(0);
        }
        Boolean etat = piece.isEtat();
        Terraium terraium_current = temrraiumRepository.findCurrentParametre();
        Date currentTime = terraium_current.getCreateTime();
        double currentTemperature = terraium_current.getTemperature();
       log.info(currentTemperature+"");
        List<Chauffage> chauffages = chauffageRepository.findAll();
        for (Chauffage c : chauffages) {
            if (c.getDateDebut().before(currentTime) && c.getDateFin().after(currentTime)) {
                log.info("temperautre"+c.getMax());
                if (c.getMax() <currentTemperature) {
                    piece.setEtat(false);
                    pieceRepository.save(piece);
                    log.info("eteindre le chauffage");

                } else if (c.getMin()>currentTemperature) {
                    piece.setEtat(true);
                    pieceRepository.save(piece);
                    log.info("offrir le chauffage");

                }else if(currentTemperature<=c.getMax()&&currentTemperature>=c.getMin()){
                    log.info("change pas letat de chauffage");
                    pieceRepository.save(piece);

                }


            }

        }


        return ResultUtil.success(piece);

    }


}