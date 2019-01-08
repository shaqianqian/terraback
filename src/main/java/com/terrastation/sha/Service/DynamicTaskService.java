package com.terrastation.sha.Service;

import com.terrastation.sha.Entity.Pulverisation;
import com.terrastation.sha.Entity.Terrarium;
import com.terrastation.sha.Repositary.PulverisationRepository;
import com.terrastation.sha.Repositary.TerrariumRepositary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ScheduledFuture;

@Service
@Slf4j
public class DynamicTaskService {
    @Autowired
    private TerrariumRepositary terrariumRepositary;

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;
    @Autowired
    private PulverisationRepository pulverisationRepository;
    @Autowired
    private DynamicTaskService dynamicTaskService;

    /**
     * 在ScheduledFuture中有一个cancel可以停止定时任务。
     * En ScheduleFUture il y a une methode<cancel>qui peut arreter le cron service
     */
    private ScheduledFuture<?> future;

    /**
     * ThreadPoolTaskScheduler est une classe de thread qui peut ouvrir le pool de thread pour gerer les threads
     * ThreadPoolTaskScheduler：线程池任务调度类，能够开启线程池进行任务调度。
     * ThreadPoolTaskScheduler.schedule()方法会创建一个定时计划ScheduledFuture，在这个方法需要添加两个参数，Runnable（线程接口类） 和CronTrigger（定时任务触发器）
     * ThreadPoolTaskScheduler peut creer un cron <ScheduledFuture>, il a besoin de deux parametres,runnable et crontrigger
     * @return
     */
    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        return new ThreadPoolTaskScheduler();
    }

    /**
     * 启动任务
     * lancer le cron
     **/
    public String startCron(String cron) {
        if (future != null) {
            future.cancel(true);
        }
        future = threadPoolTaskScheduler.schedule(new Runnable() {
            @Override
            public void run() {
                Terrarium terrarium_current = terrariumRepositary.getCurrentParameter();
                log.info("vous controlez le pulverisation en mode horaire ,Humidite courant est " + terrarium_current.getHumidite());
                Pulverisation pulverisation = pulverisationRepository.findAll().get(0);
                String heures = pulverisation.getPulverisationheure().get(0).getHeure() + "";
                Calendar c = Calendar.getInstance();
                int heureCurrent = c.get(Calendar.HOUR_OF_DAY);
                int dureeCorrespendant = pulverisation.getPulverisationheure().get(0).getDuree();
                if (pulverisation.getPulverisationheure().size() > 1) {
                    for (int i = 1; i < pulverisation.getPulverisationheure().size(); i++) {

                        heures = heures + "," + pulverisation.getPulverisationheure().get(i).getHeure();
                        if (pulverisation.getPulverisationheure().get(i).getHeure() == heureCurrent) {

                            dureeCorrespendant = pulverisation.getPulverisationheure().get(i).getDuree();
                        }

                    }
                }
                System.out.println("MyRunnable.run()，" + new Date() + " cron 值是" + cron + " duree est " + dureeCorrespendant);
                try {
                    log.info("START : Lancer le script du pulverisation");
                    Process pr = Runtime.getRuntime().exec("python ../python/pulverisation_test.py " + dureeCorrespendant);

                    BufferedReader in = new BufferedReader(new
                            InputStreamReader(pr.getInputStream()));
                    String line;
                    while ((line = in.readLine()) != null) {

                        System.out.println(line);
                    }
                    in.close();
                    pr.waitFor();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new CronTrigger(cron));
        System.out.println("DynamicTaskController.startCron()");
        return "startTask";
    }


    /**
     * s'arreter le cron
     *
     **/

    public String stopCron() {
        if (future != null) {
            future.cancel(true);
        }
        System.out.println("DynamicTaskController.stopCron()");
        return "stopTask";
    }

    /**
     * changer le cron et le re-lancer
     * 变更任务间隔，再次启动
     **/
    public String changeCron(String cron) {
        stopCron();// 先停止，在开启.
        future = threadPoolTaskScheduler.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("MyRunnable.run()，改变cron值c" + new Date() + " cron 值是" + cron);
            }
        }, new CronTrigger(cron));
        System.out.println("DynamicTaskController.changeCron()");
        return "changeCron";
    }

}