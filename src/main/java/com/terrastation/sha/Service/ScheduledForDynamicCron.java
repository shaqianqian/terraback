package com.terrastation.sha.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

@Service
@EnableScheduling
public class ScheduledForDynamicCron
//        implements SchedulingConfigurer
{

//    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
//    private static String cron = "* * * * * ?";
//
//    @Autowired
//
//    private ThreadPoolTaskScheduler threadPoolTaskScheduler;
//
//
//
//    private ScheduledFuture<?> future;
//
//
//
//    @Bean
//
//    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
//
//        return new ThreadPoolTaskScheduler();
//
//    }
//    //
////    @Override
////    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
////        taskRegistrar.addTriggerTask(() -> {
////            // 定时任务的业务逻辑
////            System.out.println("动态修改定时任务cron参数，当前时间：" + dateFormat.format(new Date()));
////        }, (triggerContext) -> {
////            // 定时任务触发，可修改定时任务的执行周期
////            CronTrigger trigger = new CronTrigger(cron);
////            Date nextExecDate = trigger.nextExecutionTime(triggerContext);
////            return nextExecDate;
////        });
////
////
////
////
////
////
////    }
//
//    public void setCron(String newCron) {
//        System.out.println("当前cron="+cron+"->将被改变为："+newCron);
//        cron = newCron;
//    }
//
//    @Override
//    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
//     scheduledTaskRegistrar.addTriggerTask(new Runnable() {
//            @Override
//            public void run(){
//                if(cron.equals("* * * * * ?"))// 任务逻辑
//                     {
//                        setCron("0 0 * * * ?");
//
//                     }
//                else{ System.out.println("时间：【" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS").format(new Date()) + "】");}
//
//            }
//        }, new Trigger(){
//            @Override
//            public Date nextExecutionTime(TriggerContext triggerContext) {
//                //任务触发，可修改任务的执行周期
//                CronTrigger trigger = new CronTrigger(cron);
//                Date nextExec = trigger.nextExecutionTime(triggerContext);
//                return nextExec;
//            }
//        });

 //   }
}
