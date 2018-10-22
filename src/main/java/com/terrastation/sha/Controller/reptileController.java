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

public class reptileController {
    Logger log = LoggerFactory.getLogger(reptileController.class);


    @Autowired
    private reptileRepository reptileRepositary;


    @RequestMapping(value = "getAll", method = RequestMethod.GET)

    public List<reptile> findall() {
        return reptileRepositary.findAll();

    }


    @RequestMapping(value = "add", method = RequestMethod.POST)

    public reptile add(@RequestParam("age") int age,@RequestParam("humity") String name) {
        reptile rep=new reptile();
        rep.setAge(age);
        rep.setName(name);
        return  reptileRepositary.save(rep);

    }



    @PutMapping("/reptile/{id}")
    public reptile updateNote(@PathVariable(value = "id") int reptileId,
                               @Valid @RequestBody reptile reptileDetails) {
        Optional<reptile> reptile = reptileRepositary.findById(reptileId);
        reptile reptile1=null;
        if(reptile.isPresent()) {
            reptile1=reptile.get();
            reptile1.setName(reptileDetails.getName());
            reptile1.setAge(reptileDetails.getAge());
        }
        reptile updatedReptile = reptileRepositary.save(reptile1);
        return updatedReptile;
    }

    @DeleteMapping("/reptile/{id}")
    public ResponseEntity<?> deleteReptile(@PathVariable(value = "id") int noteId) {
        Optional<reptile> reptile = reptileRepositary.findById(noteId);
        reptile reptile1=null;
        if(reptile.isPresent()) {
            reptile1=reptile.get();
            reptileRepositary.delete(reptile1);}
        return ResponseEntity.ok().build();
    }


}



