package com.terrastation.sha;

import com.terrastation.sha.Service.HeaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduledTask {
    @Autowired
    private HeaterService heaterService;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Integer count = 1;


    @Scheduled(fixedRate =30000)
    //30s une fois
    public void reportCurrentTime() throws InterruptedException {
        heaterService.changeEtatPieceVO();
    }


}