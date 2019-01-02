package com.terrastation.sha;

import com.terrastation.sha.Controller.TerrariumController;
import com.terrastation.sha.Entity.Terrarium;
import com.terrastation.sha.Repositary.PulverisationHeureRepository;
import com.terrastation.sha.Repositary.TerrariumRepositary;
import com.terrastation.sha.Service.InterrupteurService;
import com.terrastation.sha.Service.ScheduledForDynamicCron;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Initial implements CommandLineRunner {

    Logger log = LoggerFactory.getLogger(TerrariumController.class);
    @Autowired
    private InterrupteurService interrupteurService;
    @Autowired
    private TerrariumRepositary terrariumRepositary;
    @Autowired
    private PulverisationHeureRepository pulverisationHeureRepository;
    @Autowired
    private ScheduledForDynamicCron scheduledForDynamicCron;


    @Override
    public void run(String... args) throws Exception {
//        scheduledForDynamicCron.setCron("* 0 * * * ?");
        //scheduledForDynamicCron.setCron("0 0 * * * ?");

    }
}
