package com.terrastation.sha.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.terrastation.sha.repositary.*;
import com.terrastation.sha.domain.terraium;

import java.util.List;

@RestController

public class terraiumController {
    Logger log = LoggerFactory.getLogger(terraiumController.class);


    @Autowired
    private terraiumRepositary terraiumRepositary;


    @RequestMapping(value = "getAll", method = RequestMethod.GET)

    public List<terraium> findall() {
        return terraiumRepositary.findAll();

    }


    @RequestMapping(value = "add", method = RequestMethod.POST)

    public terraium add(@RequestParam("temperature") double temperature,@RequestParam("humity") double humity) {
        terraium t=new terraium();
        t.setHumite(temperature);
        t.setTemperature(humity);
        return  terraiumRepositary.save(t);

    }




}


