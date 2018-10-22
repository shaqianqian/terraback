package com.terrastation.sha.Controller;

import com.terrastation.sha.domain.Prereglages;
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
        Logger log = LoggerFactory.getLogger(com.terrastation.sha.Controller.reptileController.class);


        @Autowired
        private PrereglagesRepository prereglagesRepositary;


        @RequestMapping(value = "getAll", method = RequestMethod.GET)

        public List<Prereglages> findall() {
            return prereglagesRepositary.findAll();

        }


        @RequestMapping(value = "add", method = RequestMethod.POST)

        public Prereglages add(@RequestParam("typeHabitat") String typeHabitat, @RequestParam("animal") String animal,@RequestParam("temperature") double temperature , @RequestParam("humidite") double humidite,@RequestParam("pulverisation") double pulverisation) {
            Prereglages rep=new Prereglages();
            rep.setTypeHabitat(typeHabitat);
            rep.setTemperature(temperature);
            rep.setAnimal(animal);
            rep.setHumidite(humidite);
            rep.setPulverisation(pulverisation);
            return  prereglagesRepositary.save(rep);

        }



        @PutMapping("/prereglages/{id}")
        public Prereglages updateNote(@PathVariable(value = "id") int prereglagesId,
                                  @Valid @RequestBody Prereglages prereglagesDetails) {
            Optional<Prereglages> reptile = prereglagesRepositary.findById(prereglagesId);
            Prereglages prereglages1=null;
            if(reptile.isPresent()) {
                prereglages1=reptile.get();
                prereglages1.setTypeHabitat(prereglagesDetails.getTypeHabitat());
                prereglages1.setAnimal(prereglagesDetails.getAnimal());
                prereglages1.setTemperature(prereglagesDetails.getTemperature());
                prereglages1.setHumidite(prereglagesDetails.getHumidite());
                prereglages1.setPulverisation(prereglagesDetails.getPulverisation());
            }
            Prereglages updatedPrereglages = prereglagesRepositary.save(prereglages1);
            return updatedPrereglages;
        }

        @DeleteMapping("/reptile/{id}")
        public ResponseEntity<?> deleteReptile(@PathVariable(value = "id") int noteId) {
            Optional<Prereglages> prereglages = prereglagesRepositary.findById(noteId);
            Prereglages prereglages1=null;
            if(prereglages.isPresent()) {
                prereglages1=prereglages.get();
                prereglagesRepositary.delete(prereglages1);}
            return ResponseEntity.ok().build();
        }


    }




}
