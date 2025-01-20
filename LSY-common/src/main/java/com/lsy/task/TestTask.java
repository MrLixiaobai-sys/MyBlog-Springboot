package com.lsy.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TestTask {
    @Scheduled(cron = "0/5 * * * * ?")
    public void testTask() {
//        这里是每隔5秒执行一次的定时任务(任何时间都每隔5s执行)
//        任务体:例如定时刷新数据库,定时更改数据等
        System.out.println("每隔一段时间执行定时任务...");
    }
}
