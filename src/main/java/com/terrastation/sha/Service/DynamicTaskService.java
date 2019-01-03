package com.terrastation.sha.Service;

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
import java.util.Date;
import java.util.concurrent.ScheduledFuture;

@Service
@Slf4j
public class DynamicTaskService {

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    /**
     * 在ScheduledFuture中有一个cancel可以停止定时任务。
     */
    private ScheduledFuture<?> future;

    /**
     * ThreadPoolTaskScheduler：线程池任务调度类，能够开启线程池进行任务调度。
     * ThreadPoolTaskScheduler.schedule()方法会创建一个定时计划ScheduledFuture，在这个方法需要添加两个参数，Runnable（线程接口类） 和CronTrigger（定时任务触发器）
     * @return
     */
    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        return new ThreadPoolTaskScheduler();
    }

    /**
     * 启动任务
     **/
    public String startCron(String cron,int duree) {
        if (future != null) {
            future.cancel(true);
        }
        future = threadPoolTaskScheduler.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("MyRunnable.run()，" + new Date()+" cron 值是"+cron+" duree est "+duree);
                try {
                    log.info("START : Lancer le script du pulverisation");
                    Process pr = Runtime.getRuntime().exec("python ./python/pulverisation_test.py "+duree);

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
     * 启此任务
     **/

    public String stopCron() {
        if (future != null) {
            future.cancel(true);
        }
        System.out.println("DynamicTaskController.stopCron()");
        return "stopTask";
    }

    /**
     * 变更任务间隔，再次启动
     **/
    public String changeCron(String cron) {
        stopCron();// 先停止，在开启.
        future = threadPoolTaskScheduler.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("MyRunnable.run()，改变cron值c" + new Date()+" cron 值是"+cron);
            }
        }, new CronTrigger(cron));
        System.out.println("DynamicTaskController.changeCron()");
        return "changeCron";
    }

}