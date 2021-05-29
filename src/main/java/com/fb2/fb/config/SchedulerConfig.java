package com.fb2.fb.config;

import com.fb2.fb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
@EnableScheduling
@Component
public class SchedulerConfig {
    private final UserService userService;
    @Autowired
    public SchedulerConfig(UserService userService) {
        this.userService = userService;
    }




//   @Scheduled(fixedRate = 1000)
//    public void ScheduleTaskWithFixedRate(){
//        System.out.println("Method executed at every 1min delay after startup and 10 sec after. Current time is :: "+ new Date());
//        userService.deactivateUserScheduler();
//    }




}
