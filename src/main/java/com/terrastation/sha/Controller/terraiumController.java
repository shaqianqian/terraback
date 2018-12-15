package com.terrastation.sha.Controller;

import com.terrastation.sha.domain.reptile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.terrastation.sha.repositary.*;
import com.terrastation.sha.domain.terraium;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController

public class terraiumController {
    Logger log = LoggerFactory.getLogger(terraiumController.class);


    @Autowired
    private terraiumRepositary terraiumRepositary;


    @RequestMapping(value = "getAll", method = RequestMethod.GET)

    public List<terraium> findall() {
        return terraiumRepositary.findAll();

    }

    @RequestMapping(value = "getById", method = RequestMethod.GET)

    public Optional<terraium> findById(int id) {
        return terraiumRepositary.findById(id);
    }
    @RequestMapping(value = "add", method = RequestMethod.POST)

    public terraium add(@RequestParam("temperature") double temperature,@RequestParam("humity") double humity) {
        terraium t=new terraium();
        t.setHumite(temperature);
        t.setTemperature(humity);
        return  terraiumRepositary.save(t);

    }



    @PutMapping("/terrarium/{id}")
    public terraium updateTerraium(@PathVariable(value = "id") int terraiumId,
                             @Valid @RequestBody terraium terraiumDetails) {
        Optional<terraium> terraium = terraiumRepositary.findById(terraiumId);
        terraium terraium1=null;
        if(terraium.isPresent()) {
            terraium1=terraium.get();
            terraium1.setHumite(terraiumDetails.getHumite());
        }
        terraium updatedTerraium = terraiumRepositary.save(terraium1);
        return updatedTerraium;
    }

    @DeleteMapping("/terrarium/{id}")
    public ResponseEntity<?> deleteTerraium(@PathVariable(value = "id") int noteId) {
        Optional<terraium> terraium = terraiumRepositary.findById(noteId);
        terraium terraium1=null;
        if(terraium.isPresent()) {
            terraium1=terraium.get();
            terraiumRepositary.delete(terraium1);}
        return ResponseEntity.ok().build();
    }


}


